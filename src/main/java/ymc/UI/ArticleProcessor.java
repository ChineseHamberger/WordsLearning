package ymc.UI;

import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.basicelements.UserProgress;

import java.util.ArrayList;
import java.util.List;

public class ArticleProcessor {
    private WordBook wordBook;

    public ArticleProcessor(WordBook wordBook) {
        this.wordBook = wordBook;
    }

    public String processArticle(String article) {
        for (Word word : wordBook.getWords()) {
            String regex = "\\b" + word.getEnglish() + "\\b";
            article = article.replaceAll(regex, "<a href='" + word.getEnglish() + "'><b>" + word.getEnglish() + "</b></a>");
        }
        return "<html><body>" + article.replace("\n", "<br>") + "</body></html>";
    }

    public Word getWordDetails(String word) {
        for (Word w : wordBook.getWords()) {
            if (w.getEnglish().equalsIgnoreCase(word)) {
                return w;
            }
        }
        return null;
    }

    public List<Word> getWordsForLearning(int dailyLearningQuota, UserProgress progress) {
        List<Word> wordsForLearning = new ArrayList<>();
        List<Word> allWords = wordBook.getWords();
        int count = 0;

        for (Word word : allWords) {
            if (!progress.isWordLearned(word)) {
                wordsForLearning.add(word);
                count++;
                if (count >= dailyLearningQuota) {
                    break;
                }
            }
        }
        return wordsForLearning;
    }

    public List<Word> getWordsForReview(int dailyReviewQuota, UserProgress progress) {
        List<Word> wordsForReview = new ArrayList<>();
        List<Word> allWords = wordBook.getWords();
        int count = 0;

        for (Word word : allWords) {
            if (progress.isWordLearned(word) && progress.needsReview(word)) {
                wordsForReview.add(word);
                count++;
                if (count >= dailyReviewQuota) {
                    break;
                }
            }
        }
        return wordsForReview;
    }
}
