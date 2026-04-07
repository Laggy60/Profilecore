package be.laggy.profilecore.manager;

import be.laggy.profilecore.Profilecore;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyHookManager {

    private final Profilecore plugin;
    private Economy economy;

    public EconomyHookManager(Profilecore plugin) {
        this.plugin = plugin;
        setupVault();
    }

    private void setupVault() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().warning("Vault not found. Economy features disabled.");
            return;
        }

        RegisteredServiceProvider<Economy> provider = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (provider == null) {
            plugin.getLogger().warning("No economy provider found.");
            return;
        }

        this.economy = provider.getProvider();
        plugin.getLogger().info("Hooked into economy provider: " + economy.getName());
    }

    public boolean isAvailable() {
        return economy != null;
    }

    public Economy getEconomy() {
        return economy;
    }
}