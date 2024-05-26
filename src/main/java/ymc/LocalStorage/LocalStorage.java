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
    private static final String CONFIG_FILE = "/Config.dat";
    private static final String PROGRESS_FILE = "/Progress.dat";
    private static final String WORD_BOOK_DIR = "wordBooks";

    public LocalStorage() {
        File dir = new File(WORD_BOOK_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }


    public void saveUserConfig(UserConfig config, String username) {
        File dir = new File(USER_DIR+username);
        if(!dir.exists()){
            dir.mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_DIR+username+CONFIG_FILE))) {
            System.out.println("save config");
            config.showInfo();
            oos.writeObject(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public UserConfig loadUserConfig(String username) {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_DIR+username+CONFIG_FILE))) {
            UserConfig config = (UserConfig) ois.readObject();
            System.out.println("load config done");
            config.showInfo();
            return config;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public void saveUserProgress(UserProgress progress,String username) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_DIR+username+PROGRESS_FILE))) {
            System.out.println("save progress");
            oos.writeObject(progress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserProgress loadUserProgress(String username) {
        File dir = new File(USER_DIR+username);
        if(!dir.exists()){
            dir.mkdirs();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_DIR+username+PROGRESS_FILE))) {
            UserProgress progress = (UserProgress) ois.readObject();
            System.out.println("load progress done");
            return progress;
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


