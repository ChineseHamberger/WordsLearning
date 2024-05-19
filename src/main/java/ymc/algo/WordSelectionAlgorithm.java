package ymc.algo;

import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.config.UserConfig;

import java.util.ArrayList;
import java.util.List;

public class WordSelectionAlgorithm {
    public List<Word> getWordsForToday(UserProgress progress, UserConfig config, WordBook wordBook) {
        List<Word> selectedWords = new ArrayList<>();
        // Simple algorithm: pick first N words for learning quota
        for (int i = 0; i < config.getDailyLearningQuota() && i < wordBook.getWords().size(); i++) {
            selectedWords.add(wordBook.getWords().get(i));
        }
        return selectedWords;
    }
}
