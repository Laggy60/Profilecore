package be.laggy.profilecore.manager;

import be.laggy.profilecore.profile.Profile;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProfileManager {

    private final Map<UUID, Profile> profiles = new ConcurrentHashMap<>();

    public void cache(Profile profile) {
        profiles.put(profile.getUuid(), profile);
    }

    public Profile get(UUID uuid) {
        return profiles.get(uuid);
    }

    public boolean isCached(UUID uuid) {
        return profiles.containsKey(uuid);
    }

    public void remove(UUID uuid) {
        profiles.remove(uuid);
    }

    public Collection<Profile> getAll() {
        return profiles.values();
    }
}