package be.laggy.profilecore.listener;

import be.laggy.profilecore.Profilecore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final Profilecore plugin;

    public PlayerQuitListener(Profilecore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (plugin.getConfig().getBoolean("settings.save-on-quit", true)) {
            plugin.getProfileService().saveAndRemove(event.getPlayer().getUniqueId());
        }
    }
}