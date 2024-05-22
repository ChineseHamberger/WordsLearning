package ymc.LocalStorage;

import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.config.UserConfig;

import java.io.*;

import java.util.ArrayList;
import java.util.List;


public class LocalStorage {
    private static final String CONFIG_FILE = "userConfig.dat";
    private static final String PROGRESS_FILE = "userProgress.dat";
    private static final String WORD_BOOK_DIR = "wordBooks";

    public LocalStorage() {
        File dir = new File(WORD_BOOK_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void saveUserConfig(UserConfig config) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONFIG_FILE))) {
            oos.writeObject(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserConfig loadUserConfig() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONFIG_FILE))) {
            return (UserConfig) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public void saveUserProgress(UserProgress progress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PROGRESS_FILE))) {
            oos.writeObject(progress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserProgress loadUserProgress() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PROGRESS_FILE))) {
            return (UserProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public void addWordBook(WordBook wordBook) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(WORD_BOOK_DIR + "/" + wordBook.getName() + ".dat"))) {
            oos.writeObject(wordBook);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WordBook getWordBook(String name) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(WORD_BOOK_DIR + "/" + name + ".dat"))) {
            System.out.println(WORD_BOOK_DIR + "/" + name + ".dat");
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


