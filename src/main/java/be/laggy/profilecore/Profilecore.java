package be.laggy.profilecore;

import be.laggy.profilecore.command.ProfileCommand;
import be.laggy.profilecore.config.ConfigLoader;
import be.laggy.profilecore.database.DatabaseManager;
import be.laggy.profilecore.database.migration.TableInitializer;
import be.laggy.profilecore.listener.PlayerDeathListener;
import be.laggy.profilecore.listener.PlayerJoinListener;
import be.laggy.profilecore.listener.PlayerQuitListener;
import be.laggy.profilecore.manager.CacheManager;
import be.laggy.profilecore.manager.EconomyHookManager;
import be.laggy.profilecore.manager.MessageManager;
import be.laggy.profilecore.manager.ProfileManager;
import be.laggy.profilecore.profile.ProfileRepository;
import be.laggy.profilecore.profile.ProfileService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Profilecore extends JavaPlugin {

    private static Profilecore instance;

    private ConfigLoader configLoader;
    private MessageManager messageManager;
    private DatabaseManager databaseManager;
    private ProfileRepository profileRepository;
    private ProfileService profileService;
    private ProfileManager profileManager;
    private CacheManager cacheManager;
    private EconomyHookManager economyHookManager;

    public static Profilecore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        saveResourceIfNotExists("messages.yml");

        this.configLoader = new ConfigLoader(this);
        this.messageManager = new MessageManager(this);
        this.databaseManager = new DatabaseManager(this);
        this.databaseManager.connect();

        TableInitializer tableInitializer = new TableInitializer(databaseManager);
        tableInitializer.createTables();

        this.profileRepository = new ProfileRepository(databaseManager);
        this.profileService = new ProfileService(this, profileRepository);
        this.profileManager = new ProfileManager();
        this.cacheManager = new CacheManager(profileManager);
        this.economyHookManager = new EconomyHookManager(this);

        registerCommands();
        registerListeners();
        startAutosaveTask();

        getLogger().info("ProfileCore enabled successfully.");
    }

    @Override
    public void onDisable() {
        if (profileService != null) {
            profileService.saveAllCachedProfiles(profileManager);
        }

        if (databaseManager != null) {
            databaseManager.disconnect();
        }

        getLogger().info("ProfileCore disabled successfully.");
    }

    private void registerCommands() {
        ProfileCommand profileCommand = new ProfileCommand(this);
        if (getCommand("profile") != null) {
            getCommand("profile").setExecutor(profileCommand);
            getCommand("profile").setTabCompleter(profileCommand);
        }
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this), this);
    }

    private void startAutosaveTask() {
        long intervalSeconds = getConfig().getLong("settings.autosave-interval-seconds", 120L);
        long intervalTicks = intervalSeconds * 20L;

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () ->
                profileService.saveAllCachedProfiles(profileManager), intervalTicks, intervalTicks);
    }

    private void saveResourceIfNotExists(String fileName) {
        if (!new java.io.File(getDataFolder(), fileName).exists()) {
            saveResource(fileName, false);
        }
    }

    public void reloadPlugin() {
        reloadConfig();
        configLoader.reload();
        messageManager.reload();
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public ProfileRepository getProfileRepository() {
        return profileRepository;
    }

    public ProfileService getProfileService() {
        return profileService;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public EconomyHookManager getEconomyHookManager() {
        return economyHookManager;
    }
}