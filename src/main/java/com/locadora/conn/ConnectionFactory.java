package com.locadora.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
