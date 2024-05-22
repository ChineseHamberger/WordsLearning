package ymc.init;

import ymc.basicelements.Word;
import ymc.basicelements.WordBook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class WordBookInitializer {
    static String[] bookList;

    public static void getBookList(){
       bookList = tools.BookKit.getBookList();
    }

    public static void initializeBook(String bookName) throws IOException {
        WordBook wordBook = tools.JsonKit.jsonToWordBook(bookName);
        for (Word word : wordBook.getWords()) {
            word.showInfo();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("wordBooks/" + bookName + ".dat"))) {
            oos.writeObject(wordBook);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initializeAll() throws IOException {
        for (String book : bookList){

            initializeBook(book);
            System.out.println("Initialize"+book+" Done.");
        }
    }

}