package com.locadora.repository;

import com.locadora.conn.ConnectionFactory;
import com.locadora.domain.Status;
import com.locadora.domain.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VeiculoRepositoryJdbc {

    public static Veiculo slavar(Veiculo veiculo){
        String sql = "INSERT INTO `locadora_db`.`veiculo` (`placa`, `modelo`, `valor_diaria`, `status`) VALUES (?, ?, ?, ?);";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setDouble(3, veiculo.getValorDiaria());
            stmt.setString(4,veiculo.getStatus().name());

            stmt.executeUpdate();


            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    veiculo.setId(rs.getLong(1));
                }
            }

            return veiculo;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar veiculo no banco de dados", e);
        }
    }

    /**
     * Retrieves all available vehicles from the database.
     * <p>
     * Fetches all vehicles with DISPONIVEL status from the database.
     * </p>
     * <p>
     * Recupera todos os veículos disponíveis do banco de dados.
     * Busca todos os veículos com status DISPONIVEL do banco de dados.
     * </p>
     *
     * @return a list of all available vehicles / lista de todos os veículos disponíveis
     * @throws RuntimeException if an error occurs during the database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static List<Veiculo> buscarTodos() {
        String sql = "SELECT placa, modelo, valor_diaria, status FROM veiculo WHERE status = 'DISPONIVEL';";
        List<Veiculo> veiculos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Veiculo veiculo = new Veiculo(
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getDouble("valor_diaria"),
                        Status.valueOf(rs.getString("status"))
                );
                veiculos.add(veiculo);
            }

            return veiculos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculos", e);
        }
    }

    /**
     * Retrieves a vehicle by its unique ID.
     * <p>
     * Searches for a vehicle in the database using their ID.
     * </p>
     * <p>
     * Recupera um veículo por seu ID único.
     * Pesquisa por um veículo no banco de dados usando seu ID.
     * </p>
     *
     * @param id the vehicle's unique identifier / identificador único do veículo
     * @return an Optional containing the vehicle if found, empty otherwise
     *         / um Optional contendo o veículo se encontrado, vazio caso contrário
     * @throws RuntimeException if an error occurs during the database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Optional<Veiculo> buscarPorId(Long id) {
        String sql = "SELECT id,placa, modelo, valor_diaria, status FROM veiculo WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    Veiculo veiculo = new Veiculo(
                            rs.getLong("id"),
                            rs.getString("placa"),
                            rs.getString("modelo"),
                            rs.getDouble("valor_diaria"),
                            Status.valueOf(rs.getString("status"))
                    );

                    return Optional.of(veiculo);
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar veiculo por ID ", e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculo por ID ", e);
        }

        return Optional.empty();
    }

    /**
     * Retrieves a vehicle by its license plate.
     * <p>
     * Searches for a vehicle in the database using their license plate.
     * This method returns a vehicle without the ID field.
     * </p>
     * <p>
     * Recupera um veículo por sua placa de licença.
     * Pesquisa por um veículo no banco de dados usando sua placa de licença.
     * Este método retorna um veículo sem o campo ID.
     * </p>
     *
     * @param placa the vehicle's license plate / placa de licença do veículo
     * @return an Optional containing the vehicle if found, empty otherwise
     *         / um Optional contendo o veículo se encontrado, vazio caso contrário
     * @throws RuntimeException if an error occurs during the database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Optional<Veiculo> buscarPorPlaca(String placa) {
        String sql = "SELECT  placa, modelo, valor_diaria, status FROM veiculo WHERE placa = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Veiculo veiculo = new Veiculo(
                            rs.getString("placa"),
                            rs.getString("modelo"),
                            rs.getDouble("valor_diaria"),
                            Status.valueOf(rs.getString("status"))
                    );

                    return Optional.of(veiculo);
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar veiculo por PLACA ", e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculo por PLACA ", e);
        }
        return Optional.empty();
    }

    /**
     * Retrieves all information about a vehicle by its license plate.
     * <p>
     * Searches for a vehicle in the database using their license plate and includes the vehicle ID.
     * </p>
     * <p>
     * Recupera todas as informações de um veículo por sua placa de licença.
     * Pesquisa por um veículo no banco de dados usando sua placa de licença e inclui o ID do veículo.
     * </p>
     *
     * @param placa the vehicle's license plate / placa de licença do veículo
     * @return an Optional containing the vehicle with all information if found, empty otherwise
     *         / um Optional contendo o veículo com todas as informações se encontrado, vazio caso contrário
     * @throws RuntimeException if an error occurs during the database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Optional<Veiculo> buscarTodasInfoPorPlaca(String placa) {
        String sql = "SELECT id, placa, modelo, valor_diaria, status FROM veiculo WHERE placa = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Veiculo veiculo = new Veiculo(
                            rs.getLong("id"),
                            rs.getString("placa"),
                            rs.getString("modelo"),
                            rs.getDouble("valor_diaria"),
                            Status.valueOf(rs.getString("status"))
                    );

                    return Optional.of(veiculo);
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar veiculo por PLACA ", e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculo por PLACA ", e);
        }
        return Optional.empty();
    }

    /**
     * Updates a vehicle's information in the database.
     * <p>
     * Modifies an existing vehicle record with new plate, model, daily rate, and status information.
     * </p>
     * <p>
     * Atualiza as informações de um veículo no banco de dados.
     * Modifica um registro de veículo existente com novas informações de placa, modelo, taxa diária e status.
     * </p>
     *
     * @param veiculo the vehicle object with updated information
     *                / objeto veículo com informações atualizadas
     * @throws RuntimeException if an error occurs during the database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void atualizar(Veiculo veiculo) {
        String sql = "UPDATE `locadora_db`.`veiculo` SET `placa` = ?, `modelo` = ?, `valor_diaria` = ?, `status` = ? WHERE (`id` = ?);";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setDouble(3, veiculo.getValorDiaria());
            stmt.setString(4, veiculo.getStatus().name());
            stmt.setLong(5, veiculo.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar as informaçoes do veiculo, ID nao encontrado ", e);
        }
    }

    /**
     * Deletes a vehicle from the database.
     * <p>
     * Removes a vehicle record from the database by their ID.
     * </p>
     * <p>
     * Deleta um veículo do banco de dados.
     * Remove um registro de veículo do banco de dados por seu ID.
     * </p>
     *
     * @param id the vehicle's unique identifier / identificador único do veículo
     * @throws RuntimeException if an error occurs during the database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void deletar(Long id) {
        String sql = "DELETE FROM `locadora_db`.`veiculo` WHERE (`id` = ?);";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o veiculo, ID  nao encontrado ", e);
        }
    }

    /**
     * Updates only the status of a vehicle in the database.
     * <p>
     * Changes the status of a vehicle (e.g., DISPONIVEL, ALUGADO, ATRASADO, etc.).
     * </p>
     * <p>
     * Atualiza apenas o status de um veículo no banco de dados.
     * Altera o status de um veículo (ex: DISPONIVEL, ALUGADO, ATRASADO, etc.).
     * </p>
     *
     * @param id     the vehicle's unique identifier / identificador único do veículo
     * @param status the new status for the vehicle / novo status para o veículo
     * @throws RuntimeException if an error occurs during the database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void atualizarStatus(Long id, Status status){
        String sql = "UPDATE `locadora_db`.`veiculo` SET `status` = ? WHERE (`id` = ?);";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            stmt.setLong(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar as informaçoes do veiculo, ID nao encontrado ", e);
        }
    }

}
