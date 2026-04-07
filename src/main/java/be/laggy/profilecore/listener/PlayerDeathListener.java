package be.laggy.profilecore.listener;

import be.laggy.profilecore.Profilecore;
import be.laggy.profilecore.profile.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final Profilecore plugin;

    public PlayerDeathListener(Profilecore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Profile victimProfile = plugin.getProfileService().getCachedProfile(victim.getUniqueId());

        if (victimProfile != null) {
            victimProfile.addDeath();
            plugin.getProfileService().saveProfileAsync(victimProfile);
        }

        Player killer = victim.getKiller();
        if (killer != null) {
            Profile killerProfile = plugin.getProfileService().getCachedProfile(killer.getUniqueId());
            if (killerProfile != null) {
                killerProfile.addKill();
                killerProfile.addCoins(1);
                plugin.getProfileService().saveProfileAsync(killerProfile);
            }
        }
    }
}