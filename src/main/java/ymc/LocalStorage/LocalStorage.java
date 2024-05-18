package ymc.LocalStorage;

import ymc.basicelements.UserProgress;
import ymc.config.UserConfig;

public class LocalStorage {
    public void saveUserConfig(UserConfig config) {
        // Save logic...
    }

    public UserConfig loadUserConfig() {
        // Load logic...
        return new UserConfig("", 0, 0);
    }

    public void saveUserProgress(UserProgress progress) {
        // Save logic...
    }

    public UserProgress loadUserProgress() {
        // Load logic...
        return new UserProgress();
    }
}
