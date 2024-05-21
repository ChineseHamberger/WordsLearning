package ymc.basicelements;

import java.io.Serializable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserProgress implements Serializable {
    private Set<Word> learnedWords;
    private Map<Word, Integer> reviewCounts;
    private static final int REVIEW_THRESHOLD = 5;

    public UserProgress() {
        this.learnedWords = new HashSet<>();
        this.reviewCounts = new HashMap<>();
    }

    public boolean isWordLearned(Word word) {
        return learnedWords.contains(word);
    }

    public void learnWord(Word word) {
        learnedWords.add(word);
        reviewCounts.put(word, 0);
    }

    public void reviewWord(Word word) {
        reviewCounts.put(word, reviewCounts.getOrDefault(word, 0) + 1);
    }

    public boolean needsReview(Word word) {
        return reviewCounts.getOrDefault(word, 0) < REVIEW_THRESHOLD;
    }

    public Set<Word> getLearnedWords() {
        return learnedWords;
    }
}
