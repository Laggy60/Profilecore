package be.laggy.profilecore.profile;

import be.laggy.profilecore.Profilecore;
import be.laggy.profilecore.manager.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ProfileService {

    private final Profilecore plugin;
    private final ProfileRepository repository;

    public ProfileService(Profilecore plugin, ProfileRepository repository) {
        this.plugin = plugin;
        this.repository = repository;
    }

    public void loadProfile(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Profile profile = repository.findByUuid(player.getUniqueId());

            if (profile == null) {
                long now = System.currentTimeMillis();
                profile = new Profile(
                        player.getUniqueId(),
                        player.getName(),
                        0,
                        0,
                        plugin.getConfig().getLong("settings.default-coins", 0L),
                        now,
                        now,
                        0L,
                        plugin.getConfig().getString("settings.current-server-name", "lobby"),
                        now
                );
                repository.save(profile);
            }

            profile.setUsername(player.getName());
            profile.setLastSeen(System.currentTimeMillis());
            profile.setSessionStart(System.currentTimeMillis() / 1000L);

            Profile finalProfile = profile;
            Bukkit.getScheduler().runTask(plugin, () -> plugin.getProfileManager().cache(finalProfile));
        });
    }

    public Profile getCachedProfile(UUID uuid) {
        return plugin.getProfileManager().get(uuid);
    }

    public void saveProfile(Profile profile) {
        if (profile == null) {
            return;
        }

        profile.setUpdatedAt(System.currentTimeMillis());
        repository.save(profile);
    }

    public void saveProfileAsync(Profile profile) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> saveProfile(profile));
    }

    public void saveAndRemove(UUID uuid) {
        Profile profile = plugin.getProfileManager().get(uuid);
        if (profile == null) {
            return;
        }

        long nowSeconds = System.currentTimeMillis() / 1000L;
        if (profile.getSessionStart() > 0) {
            long playedThisSession = Math.max(0L, nowSeconds - profile.getSessionStart());
            profile.addPlaytime(playedThisSession);
        }

        profile.setLastSeen(System.currentTimeMillis());
        profile.setUpdatedAt(System.currentTimeMillis());

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            repository.save(profile);
            plugin.getProfileManager().remove(uuid);
        });
    }

    public void saveAllCachedProfiles(ProfileManager profileManager) {
        for (Profile profile : profileManager.getAll()) {
            saveProfile(profile);
        }
    }

    public void resetProfile(UUID uuid) {
        repository.delete(uuid);
        plugin.getProfileManager().remove(uuid);
    }
}