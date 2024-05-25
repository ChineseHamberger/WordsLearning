package ymc.basicelements;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Duration;

public class ReviewData implements Serializable {
    public static final int REVIEW_COUNT_MAX = 100;
    private int reviewCount;
    private LocalDateTime FirstLearnTime;
    private LocalDateTime lastReviewTime;

    public ReviewData(LocalDateTime firstLearnTime){
        reviewCount = 1;
        FirstLearnTime = firstLearnTime;
        lastReviewTime = firstLearnTime;
    }


    public int getReviewCount() {
        return reviewCount;
    }
    public void resetReviewCount() {
        reviewCount = 0;
    }
    public void addReviewCount() {
        if(reviewCount < REVIEW_COUNT_MAX){
            reviewCount++;
        }
    }
    public LocalDateTime getFirstLearnTime() {
        return FirstLearnTime;
    }
    public LocalDateTime getLastReviewTime() {
        return lastReviewTime;
    }
    public void setLastReviewTime(LocalDateTime lastReviewTime) {
        this.lastReviewTime = lastReviewTime;
    }

    public void showInfo() {
        System.out.println("ReviewCount: " + reviewCount);
        System.out.println("FirstLearnTime: " + FirstLearnTime);
        System.out.println("LastReviewTime: " + lastReviewTime);
    }

    public int getMaxReviewCount() {
        return REVIEW_COUNT_MAX;
    }
}
