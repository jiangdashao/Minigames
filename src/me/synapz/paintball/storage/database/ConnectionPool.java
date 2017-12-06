package me.synapz.paintball.storage.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.synapz.paintball.enums.Databases;
import me.synapz.paintball.storage.Settings;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool {

    private static HikariDataSource dataSource;

    public static void init() {
        if (dataSource == null) {
            dataSource = createDataSource(
                    Databases.USERNAME.getString(),
                    Databases.PASSWORD.getString(),
                    Databases.HOST.getString(),
                    Databases.PORT.getInteger(),
                    Databases.DATABASE.getString()
            );
            dataSource.setMaximumPoolSize(Databases.MAX_CONNECTIONS.getInteger());

            try {
                try (Connection connection = dataSource.getConnection()) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute("SELECT 1;");
                    }
                }
            } catch (Exception ex) {
                if (Databases.ENABLED.getBoolean())
                    ex.printStackTrace();
            }
        }
    }

    public static synchronized Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    private static HikariDataSource createDataSource(String username, String password, String host, int port, String database) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        return new HikariDataSource(config);
    }

}
