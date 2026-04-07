package be.laggy.profilecore.profile;

import java.util.UUID;

public class Profile {

    private final UUID uuid;
    private String username;
    private int kills;
    private int deaths;
    private long coins;
    private long firstJoin;
    private long lastSeen;
    private long playtimeSeconds;
    private String currentServer;
    private long updatedAt;
    private long sessionStart;

    public Profile(UUID uuid, String username, int kills, int deaths, long coins, long firstJoin,
                   long lastSeen, long playtimeSeconds, String currentServer, long updatedAt) {
        this.uuid = uuid;
        this.username = username;
        this.kills = kills;
        this.deaths = deaths;
        this.coins = coins;
        this.firstJoin = firstJoin;
        this.lastSeen = lastSeen;
        this.playtimeSeconds = playtimeSeconds;
        this.currentServer = currentServer;
        this.updatedAt = updatedAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public long getCoins() {
        return coins;
    }

    public long getFirstJoin() {
        return firstJoin;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public long getPlaytimeSeconds() {
        return playtimeSeconds;
    }

    public String getCurrentServer() {
        return currentServer;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public long getSessionStart() {
        return sessionStart;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public void setFirstJoin(long firstJoin) {
        this.firstJoin = firstJoin;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setPlaytimeSeconds(long playtimeSeconds) {
        this.playtimeSeconds = playtimeSeconds;
    }

    public void setCurrentServer(String currentServer) {
        this.currentServer = currentServer;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setSessionStart(long sessionStart) {
        this.sessionStart = sessionStart;
    }

    public void addKill() {
        this.kills++;
    }

    public void addDeath() {
        this.deaths++;
    }

    public void addCoins(long amount) {
        this.coins += amount;
    }

    public void addPlaytime(long seconds) {
        this.playtimeSeconds += seconds;
    }
}