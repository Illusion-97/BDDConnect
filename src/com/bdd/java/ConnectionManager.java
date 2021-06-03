package com.bdd.java;

import java.sql.*;

public final class ConnectionManager implements AutoCloseable{

    private static volatile ConnectionManager instance = null;

    private static final String url = "jdbc:mysql://localhost:3306/java?serverTimezone=UTC";
    private static final String utilisateur = "root";
    private static final String motDePasse = "";

    private static Connection connection = null;

    private ConnectionManager() {
        getConnexion();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            synchronized(ConnectionManager.class) {
                if (instance == null) {
                    instance = new ConnectionManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnexion() {
        if (connection == null) {
            synchronized(ConnectionManager.class) {
                try {
                    connection = DriverManager.getConnection(url, utilisateur, motDePasse);
                    connection.setAutoCommit(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return connection;
    }
}
