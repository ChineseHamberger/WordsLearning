package ymc.basicelements;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class UserProgress {
    private Map<String, Integer> wordFamiliarity;
    private Map<String, LocalDate> lastReviewed;

    public UserProgress() {
        this.wordFamiliarity = new HashMap<>();
        this.lastReviewed = new HashMap<>();
    }

    public Map<String, Integer> getWordFamiliarity() {
        return wordFamiliarity;
    }

    public Map<String, LocalDate> getLastReviewed() {
        return lastReviewed;
    }

    public void setWordFamiliarity(Map<String, Integer> wordFamiliarity) {
        this.wordFamiliarity = wordFamiliarity;
    }

    public void setLastReviewed(Map<String, LocalDate> lastReviewed) {
        this.lastReviewed = lastReviewed;
    }
}