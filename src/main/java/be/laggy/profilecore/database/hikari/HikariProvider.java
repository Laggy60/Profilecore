package be.laggy.profilecore.database.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariProvider {

    private HikariDataSource dataSource;

    public void connect(String host, int port, String database, String username, String password,
                        String poolName, int maximumPoolSize, int minimumIdle,
                        long connectionTimeout, long maxLifetime, long keepaliveTime) {

        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }

        HikariConfig config = new HikariConfig();

        String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useSSL=false"
                + "&serverTimezone=UTC"
                + "&allowPublicKeyRetrieval=true";

        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.setPoolName(poolName);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setMinimumIdle(minimumIdle);
        config.setConnectionTimeout(connectionTimeout);
        config.setMaxLifetime(maxLifetime);

        if (keepaliveTime > 0) {
            config.setKeepaliveTime(keepaliveTime);
        }

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");

        this.dataSource = new HikariDataSource(config);
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public boolean isConnected() {
        return dataSource != null && !dataSource.isClosed();
    }

    public void disconnect() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            dataSource = null;
        }
    }
}