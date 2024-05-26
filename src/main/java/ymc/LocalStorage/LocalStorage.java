package ymc.LocalStorage;

import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.config.UserConfig;

import java.io.*;

import java.util.ArrayList;
import java.util.List;


public class LocalStorage {
    private static final String USER_DIR = "users/";
    private static final String CONFIG_FILE = "Config.dat";
    private static final String PROGRESSES_DIR = "Progress/";
    private static final String WORD_BOOK_DIR = "wordBooks";

    public LocalStorage() {
        File dir = new File(WORD_BOOK_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }


    public void saveUserConfig(UserConfig config) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONFIG_FILE))) {
            System.out.println("save config");
            config.showInfo();
            oos.writeObject(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public UserConfig loadUserConfig() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONFIG_FILE))) {
            UserConfig config = (UserConfig) ois.readObject();
            System.out.println("load config");
            config.showInfo();
            return config;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void saveUserProgress(UserProgress progress, String bookname) {
        File dir = new File(PROGRESSES_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, bookname + ".dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            System.out.println("save progress for book: " + bookname);
            oos.writeObject(progress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public UserProgress loadUserProgress(String bookname) {
        File file = new File(PROGRESSES_DIR + bookname + ".dat");
        if (!file.exists()) {
            System.out.println("Progress file for book " + bookname + " does not exist.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            UserProgress progress = (UserProgress) ois.readObject();
            System.out.println("load progress for book: " + bookname);
            return progress;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public WordBook getWordBook(String name) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(WORD_BOOK_DIR + "/" + name + ".dat"))) {
            System.out.println("The selected word_book is "+ name);
            return (WordBook) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public List<String> listWordBooks() {
        File wordBooksDir = new File(WORD_BOOK_DIR);
        File[] wordBookFiles = wordBooksDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".dat"));
        List<String> wordBooks = new ArrayList<>();
        if (wordBookFiles != null) {
            for (File file : wordBookFiles) {
                wordBooks.add(file.getName().replace(".dat", ""));
            }
        }
        return wordBooks;
    }
}


