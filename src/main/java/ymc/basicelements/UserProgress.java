package ymc.basicelements;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class UserProgress implements Serializable {

    private Map<String, Set<Word>> learnedWords;
    private Map<String, Set<Word>> ignoredWords;
    private Map<String, Map<Word, ReviewData>> reviewCounts;
    private LocalDateTime LastLearningDate;
    private List<Word> wordsToLearn;
    private List<Word> wordsToReview;

    public UserProgress() {
        learnedWords = new HashMap<>();
        ignoredWords = new HashMap<>();
        reviewCounts = new HashMap<>();
        LastLearningDate = null;
        wordsToLearn = new ArrayList<>();
        wordsToReview = new ArrayList<>();
    }

    public boolean IsTodaySet() {
        LocalDateTime now = LocalDateTime.now();
        return LastLearningDate != null && LastLearningDate.getYear() == now.getYear()  && LastLearningDate.getDayOfYear() == now.getDayOfYear();
    }
    public List<Word> getWordsToLearn(){
        return wordsToLearn;
    }
    public void setWordsToLearn(List<Word> wordsToLearn){
        this.wordsToLearn = wordsToLearn;
    }

    public void setWordsToReview(List<Word> wordsToReview){
        this.wordsToReview = wordsToReview;
    }
    public List<Word> getWordsToReview(){
        return wordsToReview;
    }
    public void updateLastLearningDate(){
        this.LastLearningDate = LocalDateTime.now();
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
        return learnedWords.getOrDefault(bookName, Collections.emptySet()).contains(word) || ignoredWords.getOrDefault(bookName, Collections.emptySet()).contains(word);
    }

    public void learnWord(String bookName, Word word) {
        learnedWords.computeIfAbsent(bookName, k -> new HashSet<>()).add(word);
        ReviewData reviewData = new ReviewData(LocalDateTime.now());
        System.out.println("Learned word: " + word.getEnglish());
        reviewData.showInfo();
        reviewCounts.computeIfAbsent(bookName, k -> new HashMap<>()).put(word, reviewData);
        wordsToLearn.remove(word);
    }

    public void ignoreWord(String bookName, Word word) {
        ignoredWords.computeIfAbsent(bookName, k -> new HashSet<>()).add(word);
        wordsToLearn.remove(word);
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
        wordsToReview.remove(word);
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
