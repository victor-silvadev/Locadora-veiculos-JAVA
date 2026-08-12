package com.locadora.repository;

import com.locadora.conn.ConnectionFactory;
import com.locadora.domain.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepositoryJdbc {


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


    public static  List<Cliente> buscarTodos() {
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


    public static  Optional<Cliente> buscarPorId(Long id) {
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


    public static  Optional<Cliente> buscarPorCpf(String cpf) {
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


    public static  void deletar(Long id) {
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
