package com.locadora.repository;

import com.locadora.conn.ConnectionFactory;
import com.locadora.domain.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of customer data access operations.
 * <p>
 * This class handles all database operations for customer (Cliente) entities
 * using JDBC connections. It provides methods to create, read, update, and delete
 * customer records in the database.
 * </p>
 * <p>
 * Implementação JDBC das operações de acesso a dados de cliente.
 * Esta classe gerencia todas as operações de banco de dados para entidades de cliente
 * usando conexões JDBC. Fornece métodos para criar, ler, atualizar e deletar
 * registros de cliente no banco de dados.
 * </p>
 *
 * @author Locadora de Veículos
 * @version 1.0
 * @since 2024
 */
public class ClienteRepositoryJdbc {

    /**
     * Saves a new customer to the database.
     * <p>
     * Inserts a customer record into the database and sets the generated ID
     * on the customer object.
     * </p>
     * <p>
     * Salva um novo cliente no banco de dados.
     * Insere um registro de cliente no banco de dados e define o ID gerado
     * no objeto cliente.
     * </p>
     *
     * @param cliente the customer object to be saved / objeto cliente a ser salvo
     * @return the customer object with the generated database ID
     *         / objeto cliente com ID do banco de dados gerado
     * @throws RuntimeException if an error occurs during the database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Cliente salvar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, cpf, email) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());

            stmt.executeUpdate();


            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getLong(1));
                }
            }

            return cliente;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar cliente no banco de dados", e);
        }
    }

    /**
     * Retrieves all customers from the database.
     * <p>
     * Fetches all customer records from the database and returns them as a list.
     * </p>
     * <p>
     * Recupera todos os clientes do banco de dados.
     * Busca todos os registros de cliente do banco de dados e os retorna como uma lista.
     * </p>
     *
     * @return a list of all customers in the database / lista de todos os clientes do banco de dados
     * @throws RuntimeException if an error occurs during the database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static List<Cliente> buscarTodos() {
        String sql = "SELECT id, nome, cpf, email FROM cliente";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email")
                );
                clientes.add(cliente);
            }

            return clientes;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar clientes", e);
        }
    }

    /**
     * Retrieves a customer by their unique ID.
     * <p>
     * Searches for a customer in the database using their ID.
     * </p>
     * <p>
     * Recupera um cliente por seu ID único.
     * Pesquisa por um cliente no banco de dados usando seu ID.
     * </p>
     *
     * @param id the customer's unique identifier / identificador único do cliente
     * @return an Optional containing the customer if found, empty otherwise
     *         / um Optional contendo o cliente se encontrado, vazio caso contrário
     * @throws RuntimeException if an error occurs during the database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Optional<Cliente> buscarPorId(Long id) {
        String sql = "SELECT id, nome, cpf, email FROM cliente WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"));

                    return Optional.of(cliente);
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar cliente por ID ", e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente por ID ", e);
        }

        return Optional.empty();
    }

    /**
     * Retrieves a customer by their CPF.
     * <p>
     * Searches for a customer in the database using their CPF (Cadastro de Pessoas Físicas).
     * </p>
     * <p>
     * Recupera um cliente por seu CPF.
     * Pesquisa por um cliente no banco de dados usando seu CPF (Cadastro de Pessoas Físicas).
     * </p>
     *
     * @param cpf the customer's CPF / CPF do cliente
     * @return an Optional containing the customer if found, empty otherwise
     *         / um Optional contendo o cliente se encontrado, vazio caso contrário
     * @throws RuntimeException if an error occurs during the database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Optional<Cliente> buscarPorCpf(String cpf) {
        String sql = "SELECT id, nome, cpf, email FROM cliente WHERE cpf = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"));

                    return Optional.of(cliente);
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar cliente por CPF ", e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente por CPF ", e);
        }
        return Optional.empty();
    }

    /**
     * Updates a customer's information in the database.
     * <p>
     * Modifies an existing customer record with new name, CPF, and email information.
     * </p>
     * <p>
     * Atualiza as informações de um cliente no banco de dados.
     * Modifica um registro de cliente existente com novas informações de nome, CPF e email.
     * </p>
     *
     * @param cliente the customer object with updated information
     *                / objeto cliente com informações atualizadas
     * @throws RuntimeException if an error occurs during the database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void atualizar(Cliente cliente) {
        String sql = "UPDATE `locadora_db`.`cliente` SET `nome` = ?, `cpf` = ?, `email` = ? WHERE (`id` = ?);";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setLong(4, cliente.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar as informaçoes do cliente, ID nao encontrado ", e);
        }
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
     * @throws RuntimeException if an error occurs during the database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void deletar(Long id) {
        String sql = "DELETE FROM `locadora_db`.`cliente` WHERE (`id` = ?);";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o cliente pelo ID ", e);
        }
    }
}
