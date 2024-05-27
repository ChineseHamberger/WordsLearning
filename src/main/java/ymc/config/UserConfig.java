package ymc.config;

import java.io.Serializable;
import java.util.*;

public class UserConfig implements Serializable {
    private String selectedWordBook;
    private Map<String, Integer> dailyLearningQuota = new HashMap<>();
    private Map<String, Integer> dailyReviewQuota = new HashMap<>();

    private static final int DefaultDailyLearningQuota = 20;
    private static final int DefaultDailyReviewQuota = 100;

    public static int getDefaultDailyLearningQuota() {
        return DefaultDailyLearningQuota;
    }

    public static int getDefaultDailyReviewQuota() {
        return DefaultDailyReviewQuota;
    }

    public UserConfig(String selectedWordBook, int dailyLearningQuota, int dailyReviewQuota) {
        this.selectedWordBook = selectedWordBook;
        this.dailyLearningQuota.put(selectedWordBook, dailyLearningQuota);
        this.dailyReviewQuota.put(selectedWordBook, dailyReviewQuota);
    }

    public int getDailyLearningQuota() {
        return dailyLearningQuota.get(this.selectedWordBook);
    }

    public int getDailyReviewQuota() {
        return dailyReviewQuota.get(this.selectedWordBook);
    }

    public String getSelectedWordBook() {
        return selectedWordBook;
    }

    public void setSelectedWordBook(String selectedWordBook) {
        this.selectedWordBook = selectedWordBook;
    }

    public void setDailyLearningQuota(int dailyLearningQuota) {
        this.dailyLearningQuota.put(this.selectedWordBook, dailyLearningQuota);
    }

    public void setDailyReviewQuota(int dailyReviewQuota) {
        this.dailyReviewQuota.put(this.selectedWordBook, dailyReviewQuota);
    }

    public void showInfo() {
        System.out.println("    Selected WordBook: " + selectedWordBook);
        System.out.println("    Daily Learning Quota: " + dailyLearningQuota.get(this.selectedWordBook));
        System.out.println("    Daily Review Quota: " + dailyReviewQuota.get(this.selectedWordBook));
    }
}
