package ymc.basicelements;

import org.junit.jupiter.api.Test;
import ymc.LocalStorage.LocalStorage;

class WordTest {

    @Test
    void playUSSpeech() {
        LocalStorage localStorage = new LocalStorage();
        WordBook wordBook = localStorage.loadWordBook("SmallBook");
        for (Word word : wordBook.getWords()){
            System.out.println(word.getUSSpeech());
            word.playUSSpeech();
        }

    }
}