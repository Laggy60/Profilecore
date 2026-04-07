package be.laggy.profilecore.database;

import be.laggy.profilecore.Profilecore;
import be.laggy.profilecore.database.hikari.HikariProvider;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private final Profilecore plugin;
    private final HikariProvider hikariProvider;

    public DatabaseManager(Profilecore plugin) {
        this.plugin = plugin;
        this.hikariProvider = new HikariProvider();
    }

    public void connect() {
        hikariProvider.connect(
                plugin.getConfig().getString("database.host"),
                plugin.getConfig().getInt("database.port"),
                plugin.getConfig().getString("database.database"),
                plugin.getConfig().getString("database.username"),
                plugin.getConfig().getString("database.password"),
                plugin.getConfig().getString("database.pool-name"),
                plugin.getConfig().getInt("database.maximum-pool-size"),
                plugin.getConfig().getInt("database.minimum-idle"),
                plugin.getConfig().getLong("database.connection-timeout"),
                plugin.getConfig().getLong("database.max-lifetime"),
                plugin.getConfig().getLong("database.keepalive-time")
        );
    }

    public Connection getConnection() throws SQLException {
        return hikariProvider.getDataSource().getConnection();
    }

    public void disconnect() {
        hikariProvider.disconnect();
    }
}