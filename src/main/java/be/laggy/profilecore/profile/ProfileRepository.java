package be.laggy.profilecore.profile;

import be.laggy.profilecore.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProfileRepository {

    private final DatabaseManager databaseManager;

    public ProfileRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Profile findByUuid(UUID uuid) {
        String sql = "SELECT * FROM player_profiles WHERE uuid = ?";

        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, uuid.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Profile(
                            UUID.fromString(resultSet.getString("uuid")),
                            resultSet.getString("username"),
                            resultSet.getInt("kills"),
                            resultSet.getInt("deaths"),
                            resultSet.getLong("coins"),
                            resultSet.getLong("first_join"),
                            resultSet.getLong("last_seen"),
                            resultSet.getLong("playtime_seconds"),
                            resultSet.getString("current_server"),
                            resultSet.getLong("updated_at")
                    );
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public void save(Profile profile) {
        String sql = """
                INSERT INTO player_profiles (uuid, username, kills, deaths, coins, first_join, last_seen, playtime_seconds, current_server, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                username = VALUES(username),
                kills = VALUES(kills),
                deaths = VALUES(deaths),
                coins = VALUES(coins),
                last_seen = VALUES(last_seen),
                playtime_seconds = VALUES(playtime_seconds),
                current_server = VALUES(current_server),
                updated_at = VALUES(updated_at)
                """;

        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, profile.getUuid().toString());
            statement.setString(2, profile.getUsername());
            statement.setInt(3, profile.getKills());
            statement.setInt(4, profile.getDeaths());
            statement.setLong(5, profile.getCoins());
            statement.setLong(6, profile.getFirstJoin());
            statement.setLong(7, profile.getLastSeen());
            statement.setLong(8, profile.getPlaytimeSeconds());
            statement.setString(9, profile.getCurrentServer());
            statement.setLong(10, profile.getUpdatedAt());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void delete(UUID uuid) {
        String sql = "DELETE FROM player_profiles WHERE uuid = ?";

        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}