package com.exam.mymap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionPool {
    private final Map<Long, Connection> connections;
    private final String host;
    private final int port;
    private final String dbName;
    private final String username;
    private final String password;

    public ConnectionPool(String host, int port, String username, String password, String dbName) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.dbName = dbName;

        connections = new HashMap<>();
    }

    public ConnectionPool(String host, String username, String password, String dbName) {
        this(host, 3306, username, password, dbName);
    }

    public Connection getConnection() {
        long currentThreadId = Thread.currentThread().getId();

        if (!connections.containsKey(currentThreadId)) {
            createConnection(currentThreadId);
        }

        return connections.get(currentThreadId);
    }

    private void createConnection(long currentThreadId) {
        loadDriver();

        Connection connection = null;

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName
                + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull";
        try {
            connection = DriverManager.getConnection(url, username, password);
            connections.put(currentThreadId, connection);
        } catch (SQLException e) {
            closeConnection();
            throw new MyMapException(e);
        }
    }

    private void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new MyMapException(e);
        }
    }

    public void closeConnection() {
        long currentThreadId = Thread.currentThread().getId();

        if (!connections.containsKey(currentThreadId)) {
            return;
        }

        Connection connection = connections.get(currentThreadId);

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new MyMapException(e);
        }

        connections.remove(currentThreadId);
    }
}
