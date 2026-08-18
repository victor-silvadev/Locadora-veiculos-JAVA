package com.locadora.repository;

import com.locadora.domain.Status;
import com.locadora.domain.Veiculo;

import java.util.List;
import java.util.Optional;

/**
 * Repository facade for vehicle data access operations.
 * <p>
 * This class provides a simplified interface to VeiculoRepositoryJdbc, handling
 * all vehicle-related database operations such as save, retrieve, update, and delete.
 * It acts as a bridge between the service layer and the JDBC implementation.
 * </p>
 * <p>
 * Fachada de repositório para operações de acesso a dados de veículo.
 * Esta classe fornece uma interface simplificada para VeiculoRepositoryJdbc, gerenciando
 * todas as operações de banco de dados relacionadas a veículos, como salvar, recuperar, atualizar e deletar.
 * Atua como uma ponte entre a camada de serviço e a implementação JDBC.
 * </p>
 *
 * @author Locadora de Veículos
 * @version 1.0
 * @since 2024
 */
public class VeiculoRepository {

    /**
     * Saves a vehicle to the database.
     * <p>
     * Delegates to VeiculoRepositoryJdbc to persist the vehicle object.
     * </p>
     * <p>
     * Salva um veículo no banco de dados.
     * Delega para VeiculoRepositoryJdbc para persistir o objeto veículo.
     * </p>
     *
     * @param veiculo the vehicle object to be saved / objeto veículo a ser salvo
     * @return the saved vehicle with the generated ID / veículo salvo com ID gerado
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Veiculo salvar(Veiculo veiculo) {
        VeiculoRepositoryJdbc.slavar(veiculo);
        return veiculo;
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
     * @return a list of available vehicles / lista de veículos disponíveis
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static List<Veiculo> buscarTodosVeiculos() {
        return VeiculoRepositoryJdbc.buscarTodos();
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
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Optional<Veiculo> buscarVeiculoPorId(Long id) {
        return VeiculoRepositoryJdbc.buscarPorId(id);
    }

    /**
     * Retrieves a vehicle by its license plate.
     * <p>
     * Searches for a vehicle in the database using their license plate.
     * </p>
     * <p>
     * Recupera um veículo por sua placa de licença.
     * Pesquisa por um veículo no banco de dados usando sua placa de licença.
     * </p>
     *
     * @param placa the vehicle's license plate / placa de licença do veículo
     * @return an Optional containing the vehicle if found, empty otherwise
     *         / um Optional contendo o veículo se encontrado, vazio caso contrário
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Optional<Veiculo> buscarVeiculoporplaca(String placa) {
        return VeiculoRepositoryJdbc.buscarPorPlaca(placa);
    }

    /**
     * Retrieves all information about a vehicle by its license plate.
     * <p>
     * Searches for a vehicle in the database using their license plate and returns
     * all available information including the vehicle ID.
     * </p>
     * <p>
     * Recupera todas as informações de um veículo por sua placa de licença.
     * Pesquisa um veículo no banco de dados usando sua placa de licença e retorna
     * todas as informações disponíveis incluindo o ID do veículo.
     * </p>
     *
     * @param placa the vehicle's license plate / placa de licença do veículo
     * @return an Optional containing the vehicle with all information if found, empty otherwise
     *         / um Optional contendo o veículo com todas as informações se encontrado, vazio caso contrário
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Optional<Veiculo> buscarTodasInfoVeiculoporplaca(String placa) {
        return VeiculoRepositoryJdbc.buscarTodasInfoPorPlaca(placa);
    }

    /**
     * Updates a vehicle's information in the database.
     * <p>
     * Modifies an existing vehicle's details in the database.
     * </p>
     * <p>
     * Atualiza as informações de um veículo no banco de dados.
     * Modifica os detalhes de um veículo existente no banco de dados.
     * </p>
     *
     * @param veiculo the vehicle object with updated information
     *                / objeto veículo com informações atualizadas
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void atualizar(Veiculo veiculo) {
        VeiculoRepositoryJdbc.atualizar(veiculo);
    }

    /**
     * Updates a vehicle's status in the database.
     * <p>
     * Changes the status of a vehicle (e.g., DISPONIVEL, ALUGADO, etc.).
     * </p>
     * <p>
     * Atualiza o status de um veículo no banco de dados.
     * Altera o status de um veículo (ex: DISPONIVEL, ALUGADO, etc.).
     * </p>
     *
     * @param id     the vehicle's unique identifier / identificador único do veículo
     * @param status the new status for the vehicle / novo status para o veículo
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void atualizarStatusVeiculo(Long id, Status status) {
        VeiculoRepositoryJdbc.atualizarStatus(id, status);
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
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void deletar(Long id) {
        VeiculoRepositoryJdbc.deletar(id);
    }
}
