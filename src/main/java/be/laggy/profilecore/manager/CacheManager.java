package be.laggy.profilecore.manager;

public class CacheManager {

    private final ProfileManager profileManager;

    public CacheManager(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }
}