package com.locadora.repository;

import com.locadora.domain.Cliente;

import java.util.Optional;

/**
 * Repository facade for customer data access operations.
 * <p>
 * This class provides a simplified interface to ClienteRepositoryJdbc, handling
 * all customer-related database operations such as save, retrieve, update, and delete.
 * It acts as a bridge between the service layer and the JDBC implementation.
 * </p>
 * <p>
 * Fachada de repositório para operações de acesso a dados de cliente.
 * Esta classe fornece uma interface simplificada para ClienteRepositoryJdbc, gerenciando
 * todas as operações de banco de dados relacionadas a clientes, como salvar, recuperar, atualizar e deletar.
 * Atua como uma ponte entre a camada de serviço e a implementação JDBC.
 * </p>
 *
 * @author Locadora de Veículos
 * @version 1.0
 * @since 2024
 */
public class ClienteRepository {

    /**
     * Saves a customer to the database.
     * <p>
     * Delegates to ClienteRepositoryJdbc to persist the customer object.
     * </p>
     * <p>
     * Salva um cliente no banco de dados.
     * Delega para ClienteRepositoryJdbc para persistir o objeto cliente.
     * </p>
     *
     * @param cliente the customer object to be saved / objeto cliente a ser salvo
     * @return the saved customer with the generated ID / cliente salvo com ID gerado
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Cliente salvar(Cliente cliente) {
        ClienteRepositoryJdbc.salvar(cliente);
        return cliente;
    }

    /**
     * Retrieves and displays all customers from the database.
     * <p>
     * Fetches all customers and prints them to the console via System.out.println.
     * </p>
     * <p>
     * Recupera e exibe todos os clientes do banco de dados.
     * Busca todos os clientes e os imprime no console via System.out.println.
     * </p>
     *
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void buscarTodos() {
        System.out.println(ClienteRepositoryJdbc.buscarTodos());
    }

    /**
     * Retrieves a customer by their ID.
     * <p>
     * Fetches a customer from the database using their unique identifier.
     * </p>
     * <p>
     * Recupera um cliente por seu ID.
     * Busca um cliente no banco de dados usando seu identificador único.
     * </p>
     *
     * @param id the customer's unique identifier / identificador único do cliente
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void buscarPorId(Long id) {
        ClienteRepositoryJdbc.buscarPorId(id);
    }

    /**
     * Retrieves a customer by their CPF.
     * <p>
     * Searches for a customer in the database using their CPF (Cadastro de Pessoas Físicas).
     * </p>
     * <p>
     * Recupera um cliente por seu CPF.
     * Pesquisa um cliente no banco de dados usando seu CPF (Cadastro de Pessoas Físicas).
     * </p>
     *
     * @param cpf the customer's CPF / CPF do cliente
     * @return an Optional containing the customer if found, empty otherwise
     *         / um Optional contendo o cliente se encontrado, vazio caso contrário
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Optional<Cliente> buscarPorCpf(String cpf) {
        return ClienteRepositoryJdbc.buscarPorCpf(cpf);
    }

    /**
     * Updates a customer's information in the database.
     * <p>
     * Modifies an existing customer's details in the database.
     * </p>
     * <p>
     * Atualiza as informações de um cliente no banco de dados.
     * Modifica os detalhes de um cliente existente no banco de dados.
     * </p>
     *
     * @param cliente the customer object with updated information
     *                / objeto cliente com informações atualizadas
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void atualizar(Cliente cliente) {
        ClienteRepositoryJdbc.atualizar(cliente);
    }

    /**
     * Deletes a customer from the database.
     * <p>
     * Removes a customer record from the database by their ID.
     * </p>
     * <p>
     * Deleta um cliente do banco de dados.
     * Remove um registro de cliente do banco de dados por seu ID.
     * </p>
     *
     * @param id the customer's unique identifier / identificador único do cliente
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void deletar(long id) {
        ClienteRepositoryJdbc.deletar(id);
    }

}
