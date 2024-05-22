package ymc.basicelements;

import java.io.*;
import java.util.*;

public class UserProgress implements Serializable {
    private Map<String, Set<Word>> learnedWords;
    private Map<String, Map<Word, Integer>> reviewCounts;
    private static final int REVIEW_THRESHOLD = 5;

    public UserProgress() {
        this.learnedWords = new HashMap<>();
        this.reviewCounts = new HashMap<>();
    }

    public boolean isWordLearned(String bookName, Word word) {
        return learnedWords.getOrDefault(bookName, Collections.emptySet()).contains(word);
    }

    public void learnWord(String bookName, Word word) {
        learnedWords.computeIfAbsent(bookName, k -> new HashSet<>()).add(word);
        reviewCounts.computeIfAbsent(bookName, k -> new HashMap<>()).put(word, 0);
    }

    public void reviewWord(String bookName, Word word) {
        reviewCounts.computeIfAbsent(bookName, k -> new HashMap<>())
                .put(word, reviewCounts.get(bookName).getOrDefault(word, 0) + 1);
    }

    public boolean needsReview(String bookName, Word word) {
        return reviewCounts.getOrDefault(bookName, Collections.emptyMap()).getOrDefault(word, 0) < REVIEW_THRESHOLD;
    }

    public List<Word> getWordsForLearning(String bookName, List<Word> allWords, int dailyLearningQuota) {
        List<Word> wordsToLearn = new ArrayList<>();
        for (Word word : allWords) {
            if (!isWordLearned(bookName, word)) {
                wordsToLearn.add(word);
                if (wordsToLearn.size() >= dailyLearningQuota) {
                    break;
                }
            }
        }
        return wordsToLearn;
    }

    public List<Word> getWordsForReview(String bookName, int dailyReviewQuota) {
        List<Word> wordsToReview = new ArrayList<>();
        Set<Word> learnedSet = learnedWords.getOrDefault(bookName, Collections.emptySet());
        for (Word word : learnedSet) {
            if (needsReview(bookName, word)) {
                wordsToReview.add(word);
                if (wordsToReview.size() >= dailyReviewQuota) {
                    break;
                }
            }
        }
        return wordsToReview;
    }

    // Save and load user progress from file
    public static UserProgress loadProgress(String username) {
        UserProgress progress = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Progress/" + username + ".dat"))) {
            progress = (UserProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            progress = new UserProgress();
        }
        return progress;
    }

    public void saveProgress(String username) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Progress/" + username + ".dat"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
