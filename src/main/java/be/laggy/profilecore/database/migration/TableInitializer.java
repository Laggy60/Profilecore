package be.laggy.profilecore.database.migration;

import be.laggy.profilecore.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableInitializer {

    private final DatabaseManager databaseManager;

    public TableInitializer(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void createTables() {
        String sql = """
                CREATE TABLE IF NOT EXISTS player_profiles (
                    uuid VARCHAR(36) NOT NULL PRIMARY KEY,
                    username VARCHAR(16) NOT NULL,
                    kills INT NOT NULL DEFAULT 0,
                    deaths INT NOT NULL DEFAULT 0,
                    coins BIGINT NOT NULL DEFAULT 0,
                    first_join BIGINT NOT NULL,
                    last_seen BIGINT NOT NULL,
                    playtime_seconds BIGINT NOT NULL DEFAULT 0,
                    current_server VARCHAR(64) NULL,
                    updated_at BIGINT NOT NULL
                )
                """;

        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}