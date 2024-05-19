package ymc.init;

import ymc.basicelements.Word;
import ymc.basicelements.WordBook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class WordBookInitializer {
    public static void main(String[] args) {
        WordBook exampleBook = new WordBook("ExampleBook", Arrays.asList(
                new Word("abandon", "放弃", "He decided to abandon his studies."),
                new Word("ability", "能力", "She has the ability to sing beautifully."),
                new Word("able", "能够", "I am able to swim.")
        ));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("wordBooks/ExampleBook.dat"))) {
            oos.writeObject(exampleBook);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("WordBook ExampleBook.dat has been initialized.");
    }
}