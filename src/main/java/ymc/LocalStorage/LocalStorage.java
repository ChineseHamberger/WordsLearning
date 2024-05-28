package ymc.LocalStorage;

import settings.GlobalSetting;
import tools.BookKit;
import ymc.basicelements.UserProgress;
import ymc.basicelements.WordBook;
import ymc.config.UserConfig;
import ymc.init.WordBookInitializer;

import java.io.*;

import java.util.ArrayList;
import java.util.List;


public class LocalStorage {
    private static final String USERS_DIR = "users/";
    private static final String CONFIG_FILE = "Config.dat";
    private static final String PROGRESSES_DIR = "Progresses/";
    private static final String WORD_BOOK_DIR = "wordBooks";
    private static final String SETTINGS_DIR = "settings/";

    private static final String GLOBAL_SETTING_FILE = "GlobalSetting.dat";

    public LocalStorage() {
        File dir = new File(WORD_BOOK_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File usersDir = new File(USERS_DIR);
        if (!usersDir.exists()) {
            usersDir.mkdir();
        }
    }
    public GlobalSetting loadGlobalSettings(){
        File dir = new File(SETTINGS_DIR);
        if (!dir.exists()) {
            dir.mkdir(); // 创建USER_DIR目录
        }
        File file = new File(SETTINGS_DIR+"/"+GLOBAL_SETTING_FILE);
        if (!file.exists()) {
            System.out.println("Global setting file does not exist.");
            return new GlobalSetting();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("load global setting");
            GlobalSetting globalSetting = (GlobalSetting) ois.readObject();
            globalSetting.showInfo();
            return globalSetting;
        }
        catch (IOException | ClassNotFoundException e){
            System.out.println("load global setting error");
            e.printStackTrace();
            System.err.println("An error occurred. Exiting the program.");
            System.exit(-1); // 终止程序
            return null;
        }
    }
    public void saveGlobalSettings(GlobalSetting setting){
        File dir = new File(SETTINGS_DIR);
        if (!dir.exists()){
            dir.mkdir();
        }
        File file = new File(SETTINGS_DIR+"/"+GLOBAL_SETTING_FILE);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            System.out.println("save global setting");
            oos.writeObject(setting);
        }
        catch (IOException e){
            System.out.println("save global setting error");
            e.printStackTrace();
        }
    }
    public void saveUserConfig(String username, UserConfig config) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_DIR+username+"/"+CONFIG_FILE))) {
            System.out.println("save config");
            config.showInfo();
            oos.writeObject(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public UserConfig loadUserConfig(String username) {

        File dir = new File(USERS_DIR+username+"/");
        if (!dir.exists()) {
            dir.mkdir(); // 创建USER_DIR目录
        }
        File file = new File(USERS_DIR+ username +"/"+ CONFIG_FILE);
        if (!file.exists()) {
            System.out.println("Config file does not exist.");
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            UserConfig config = (UserConfig) ois.readObject();
            System.out.println("load config");
            config.showInfo();
            return config;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("load config error");
            e.printStackTrace();
            System.err.println("An error occurred. Exiting the program.");
            System.exit(-1); // 终止程序
            return null;
        }
    }

    public void saveUserProgress(String username, UserProgress progress, String bookname) {
        File dir = new File(USERS_DIR+ username +"/"+ PROGRESSES_DIR);
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
    public UserProgress loadUserProgress(String username, String bookname) {
        File userDirFile = new File(USERS_DIR);
        if (!userDirFile.exists()) {
            userDirFile.mkdir(); // 创建USER_DIR目录
        }

        File file = new File(USERS_DIR+ username +"/"+ PROGRESSES_DIR + bookname + ".dat");
        if (!file.exists()) {
            System.out.println("Progress file for book " + bookname + " does not exist.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            UserProgress progress = (UserProgress) ois.readObject();
            System.out.println("load progress for book: " + bookname);
            return progress;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("load progress error");
            e.printStackTrace();
            System.err.println("An error occurred. Exiting the program.");
            System.exit(-1); // 终止程序
            return null;
        }
    }

    public WordBook loadWordBook(String bookName) {
        File file = new File(WORD_BOOK_DIR +"/"+bookName +".dat");
        if (!file.exists()) {
            try{
                WordBookInitializer.initializeBook(bookName);
            }catch (Exception e){
                System.out.println("Word book file does not exist.");
                return null;
            }
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("The selected word_book is "+ bookName);
            return (WordBook) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("load word book error");
            e.printStackTrace();
            System.exit(-1);
            return null;
        }
    }

    public List<String> listWordBooks() {
        return BookKit.getBookList();
    }
}


