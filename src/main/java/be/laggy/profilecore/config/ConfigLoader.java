package be.laggy.profilecore.config;

import be.laggy.profilecore.Profilecore;

public class ConfigLoader {

    private final Profilecore plugin;

    public ConfigLoader(Profilecore plugin) {
        this.plugin = plugin;
    }

    public void reload() {
        plugin.reloadConfig();
    }
}