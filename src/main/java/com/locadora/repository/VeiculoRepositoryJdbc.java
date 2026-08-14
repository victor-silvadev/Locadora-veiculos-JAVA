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
        String sql = "INSERT INTO `locadora_db`.`veiculo` (`placa`, `modelo`, `valor_diaria`, `status`) VALUES (?, ?, ?, `DISPONIVEL`);";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setDouble(3, veiculo.getValorDiaria());

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

    public static  Optional<Veiculo> buscarPorPlaca(String placa) {
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


    public static  Optional<Veiculo> buscarTodasInfoPorPlaca(String placa) {
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


    public static  void deletar(Long id) {
        String sql = "DELETE FROM `locadora_db`.`veiculo` WHERE (`id` = ?);";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o veiculo, ID  nao encontrado ", e);
        }
    }

}
