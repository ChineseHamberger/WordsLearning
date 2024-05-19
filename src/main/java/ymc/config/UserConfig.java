package ymc.config;

import java.io.Serializable;

public class UserConfig implements Serializable {
    private String selectedWordBook;
    private int dailyLearningQuota;
    private int dailyReviewQuota;

    public UserConfig(String selectedWordBook, int dailyLearningQuota, int dailyReviewQuota) {
        this.selectedWordBook = selectedWordBook;
        this.dailyLearningQuota = dailyLearningQuota;
        this.dailyReviewQuota = dailyReviewQuota;
    }

    public int getDailyLearningQuota() {
        return dailyLearningQuota;
    }

    public int getDailyReviewQuota() {
        return dailyReviewQuota;
    }

    public String getSelectedWordBook() {
        return selectedWordBook;
    }

    public void setSelectedWordBook(String selectedWordBook) {
        this.selectedWordBook = selectedWordBook;
    }

    public void setDailyLearningQuota(int dailyLearningQuota) {
        this.dailyLearningQuota = dailyLearningQuota;
    }

    public void setDailyReviewQuota(int dailyReviewQuota) {
        this.dailyReviewQuota = dailyReviewQuota;
    }

}
