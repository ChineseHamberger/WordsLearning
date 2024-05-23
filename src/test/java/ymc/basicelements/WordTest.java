package ymc.basicelements;

import org.junit.jupiter.api.Test;
import ymc.init.WordBookInitializer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {

    @Test
    void playUSSpeech() {
        WordBook wordBook = WordBookLoader.loadWordBook("SmallBook");
        for (Word word : wordBook.getWords()){
            System.out.println(word.getUSSpeech());
            word.playUSSpeech();
        }

    }
}