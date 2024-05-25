package ymc.basicelements;

import ymc.algo.WordSelectionAlgorithm;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class UserProgress implements Serializable {

    private Map<String, Set<Word>> learnedWords;
    private Map<String, Map<Word, ReviewData>> reviewCounts;
    private static final int REVIEW_THRESHOLD = 5;

    public UserProgress() {
        this.learnedWords = new HashMap<>();
        this.reviewCounts = new HashMap<>();
    }

    public Set<Word> getLearnedWords(String bookName) {
        Set<Word> emptyLearnedWords = new HashSet<>();
        return learnedWords.getOrDefault(bookName, emptyLearnedWords);
    }

    public Map<Word, ReviewData> getReviewCounts(String bookName) {
        Map<Word, ReviewData> emptyReviewCounts = new HashMap<>();
        return reviewCounts.getOrDefault(bookName, emptyReviewCounts);
    }

    public boolean isWordLearned(String bookName, Word word) {
        return learnedWords.getOrDefault(bookName, Collections.emptySet()).contains(word);
    }

    public void learnWord(String bookName, Word word) {
        learnedWords.computeIfAbsent(bookName, k -> new HashSet<>()).add(word);
        ReviewData reviewData = new ReviewData(LocalDateTime.now());
        System.out.println("Learned word: " + word.getEnglish());
        reviewData.showInfo();
        reviewCounts.computeIfAbsent(bookName, k -> new HashMap<>()).put(word, reviewData);
    }

    public void reviewWord(String bookName, Word word) {
        ReviewData reviewData = reviewCounts.getOrDefault(bookName, Collections.emptyMap()).get(word);
        if (reviewData == null){
            throw new IllegalArgumentException("Word not found in review counts");
        }
        reviewData.addReviewCount();
        reviewData.setLastReviewTime(LocalDateTime.now());
        reviewData.showInfo();
        reviewCounts.get(bookName).put(word, reviewData);
    }



    public void showInfo(String bookName) {
        Set<Word> myLearnedWords = getLearnedWords(bookName);
        System.out.println("Learned words for " + bookName + ":");
        for (Word word : myLearnedWords) {
            System.out.println("    "+word.getEnglish() +" "+word.getTranChinese());
        }
        Map<Word, ReviewData> myReviewCounts = getReviewCounts(bookName);
        System.out.println("Review counts for " + bookName + ":");
        for (Map.Entry<Word, ReviewData> entry : myReviewCounts.entrySet()) {
            Word word = entry.getKey();
            ReviewData reviewData = entry.getValue();
            System.out.println("    " + word.getEnglish() + " 's reviewCount is " + reviewData.getReviewCount());
        }
    }
}
