package ymc.basicelements;

import java.io.*;

public class WordBookLoader {
    private static final String BOOKS_PATH = "wordBooks/";
    public static WordBook loadWordBook(String bookName) {
        WordBook wordBook = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKS_PATH + "SmallBook"+ ".dat"))) {
            System.out.println("Loading " + bookName + "...");
            wordBook = (WordBook) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return wordBook;
    }
}
