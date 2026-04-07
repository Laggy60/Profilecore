package be.laggy.profilecore.manager;

import be.laggy.profilecore.Profilecore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageManager {

    private final Profilecore plugin;
    private File file;
    private FileConfiguration configuration;

    public MessageManager(Profilecore plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        this.file = new File(plugin.getDataFolder(), "messages.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        load();
    }

    public String get(String path) {
        return configuration.getString(path, "&cMissing message: " + path);
    }
}