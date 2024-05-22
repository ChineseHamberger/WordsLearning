package ymc.basicelements;

import java.io.*;

public class WordBookLoader {
    public static WordBook loadWordBook(String bookName) {
        WordBook wordBook = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("WordBooks/" + bookName + ".dat"))) {
            wordBook = (WordBook) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return wordBook;
    }
}
