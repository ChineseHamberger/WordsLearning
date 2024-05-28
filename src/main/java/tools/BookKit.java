package tools;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;


public class BookKit {
    private BookKit(){};

    static String BOOKS_DIR = "books/book_json";
    public static List<String> getBookList()
    {
        //从目录中读取书的名字
        File directory = new File(BOOKS_DIR);
        String[] book = directory.list();
        System.out.println(book);

        String[] bookNames = directory.list((dir, name) -> name.endsWith(".json"));
        assert bookNames != null;
        for (int i = 0; i < bookNames.length; i++) {
            bookNames[i] = bookNames[i].substring(0, bookNames[i].length() - 5);
        }
        return new java.util.ArrayList<String>(Arrays.asList(bookNames));
    }


}
