package tools;

import java.io.File;
import java.io.FilenameFilter;


public class BookKit {
    private BookKit(){};

    static String BOOKS_DIR = "books/book_json";
    public static String[] getBookList()
    {
        //从目录中读取书的名字
        File directory = new File(BOOKS_DIR);

        String[] bookNames = directory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        });

        if (bookNames != null) {
            for (int i = 0; i < bookNames.length; i++) {
                bookNames[i] = bookNames[i].substring(0, bookNames[i].length() - 5);
            }
        }

        return bookNames;
    }


}
