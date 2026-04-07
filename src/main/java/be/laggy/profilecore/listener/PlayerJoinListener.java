package be.laggy.profilecore.listener;

import be.laggy.profilecore.Profilecore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final Profilecore plugin;

    public PlayerJoinListener(Profilecore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.getProfileService().loadProfile(event.getPlayer());
    }
}