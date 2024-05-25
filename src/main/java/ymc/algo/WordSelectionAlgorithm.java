package ymc.algo;

import ymc.basicelements.ReviewData;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.config.UserConfig;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class WordSelectionAlgorithm {

    private WordSelectionAlgorithm() {}

    public static double weightOf(ReviewData reviewData) {
        int pivot = 100;
        double a = 1.0 / reviewData.getMaxReviewCount() * Math.log(pivot);
        double weight = Math.exp(-a * reviewData.getReviewCount());
        Duration duration = Duration.between(reviewData.getFirstLearnTime(), LocalDateTime.now());
        int days = (int) (duration.toDays() + 1);
        if (days == 1 || days == 2 || days == 4 || days == 7 || days == 15) {
            weight *= 10;
        }
        return weight;
    }

    public static List<Word> getWordsForLearning(WordBook wordBook, UserProgress userProgress, UserConfig userConfig) {
        int cnt = 0, dailyLearningQuota = userConfig.getDailyLearningQuota();
        List<Word> allWords = wordBook.getWords();
        List<Word> wordsForLearning = new ArrayList<>();

        for (Word word : allWords) {
            if (cnt >= dailyLearningQuota) {
                break;
            }
            if (!userProgress.isWordLearned(wordBook.getName(), word)) {
                wordsForLearning.add(word);
                cnt++;
            }
        }
        return wordsForLearning;
    }

    public static List<Word> getWordsForReview(WordBook wordBook, UserProgress progress, UserConfig config) {
        String bookName = wordBook.getName();
        double allReviewWeight = 0;
        int dailyReviewQuota = config.getDailyReviewQuota();

        Map<Word, ReviewData> reviewCounts = progress.getReviewCounts(bookName);
        Map<Word, Double> weights = new HashMap<>();

        if (reviewCounts.size() <= dailyReviewQuota) {
            return new ArrayList<>(reviewCounts.keySet());
        }

        for (Word learnedWord : reviewCounts.keySet()) {
            ReviewData reviewData = reviewCounts.get(learnedWord);
            double reviewWeight = weightOf(reviewData);
            allReviewWeight += reviewWeight;
            weights.put(learnedWord, reviewWeight);
        }

        if (weights.isEmpty()) {
            return Collections.emptyList();
        }

        List<Word> selectedWords = new ArrayList<>();
        Random random = new Random();
        while (selectedWords.size() < dailyReviewQuota && !weights.isEmpty()) {
            double randomWeight = random.nextDouble() * allReviewWeight;
            double currentWeight = 0;
            Word chosenWord = null;

            for (Map.Entry<Word, Double> entry : weights.entrySet()) {
                currentWeight += entry.getValue();
                if (randomWeight < currentWeight) {
                    chosenWord = entry.getKey();
                    break;
                }
            }

            if (chosenWord != null) {
                selectedWords.add(chosenWord);
                allReviewWeight -= weights.remove(chosenWord);
            }
        }

        return selectedWords;
    }
}
