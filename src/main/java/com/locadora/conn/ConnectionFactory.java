package com.locadora.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility factory for obtaining JDBC connections.
 * <p>
 * Centralizes connection configuration and exposes a single static method
 * to obtain a new Connection to the MySQL database used by the project.
 * </p>
 * <p>
 * Fábrica utilitária para obtenção de conexões JDBC.
 * Centraliza a configuração de conexão e expõe um método estático para
 * obter uma nova Connection para o banco de dados MySQL usado no projeto.
 * </p>
 */
public class ConnectionFactory {

    // Configurações da URL de Conexão com o MySQL Docker
    private static final String URL = "jdbc:mysql://localhost:3307/locadora_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";

    private ConnectionFactory() {
    }

    /**
     * Obtém uma nova conexão com o banco de dados MySQL.
     * @return Objeto Connection pronto para uso.
     * @throws SQLException Caso ocorra erro de conexão.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
