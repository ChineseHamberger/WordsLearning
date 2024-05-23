package ymc.main;

import ymc.UI.UserInterface;
import ymc.init.WordBookInitializer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //此处无需WordBookInitializer.initializeAll()，因为UserInterface会自动调用loadWordBook(),会自动初始化选定的单词书
        UserInterface ui = new UserInterface();
        ui.start();
    }
}
