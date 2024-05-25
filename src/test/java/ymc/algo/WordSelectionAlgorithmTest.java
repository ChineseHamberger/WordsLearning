package ymc.algo;

import org.junit.jupiter.api.Test;
import ymc.LocalStorage.LocalStorage;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.config.UserConfig;

import java.io.ObjectInputFilter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordSelectionAlgorithmTest {

    @Test
    void getWordsForReview() {
        LocalStorage storage = new LocalStorage();
        WordBook wordBook = storage.getWordBook("SmallBook");
        UserConfig config = new UserConfig("SmallBook",2,2);
        config.showInfo();
        UserProgress progress = new UserProgress();
        progress.showInfo("SmallBook");

        List<Word> allWords = wordBook.getWords();
        System.out.println("All words:");
        for (Word word:allWords){
            System.out.println("    "+word.getEnglish());
            progress.learnWord("SmallBook",word);
        }
        progress.showInfo("SmallBook");

        List<Word> reviewWords = WordSelectionAlgorithm.getWordsForReview(wordBook,progress,config);
        System.out.println("Review words:");
        for(Word word:reviewWords){
            System.out.println("    "+word.getEnglish());
        }

        assertTrue(reviewWords.size()==2);
        for(Word word:allWords){
            for (int i=0;i<50;i++){
                progress.reviewWord("SmallBook",word);
            }
            break;
        }

        progress.showInfo("SmallBook");
        reviewWords = WordSelectionAlgorithm.getWordsForReview(wordBook,progress,config);
        System.out.println("Review words:");
        for(Word word:reviewWords){
            System.out.println("    "+word.getEnglish());
        }
    }
}