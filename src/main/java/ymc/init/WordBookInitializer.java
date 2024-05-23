package ymc.init;

import ymc.basicelements.Word;
import ymc.basicelements.WordBook;

import java.io.*;
import java.util.Arrays;

public class WordBookInitializer {
    static String[] bookList;

    public static void getBookList(){
       bookList = tools.BookKit.getBookList();
    }

    public static void initializeBook(String bookName) throws IOException {
        WordBook wordBook = tools.JsonKit.jsonToWordBook(bookName);
        String filePath = "wordBooks/" + bookName + ".dat";
//        for (Word word : wordBook.getWords()) {
//            word.showInfo();
//        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            System.out.println("Initialize"+bookName+" Done.");
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