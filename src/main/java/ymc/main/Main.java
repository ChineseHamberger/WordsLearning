package ymc.main;

import ymc.UI.UserInterface;
import ymc.init.WordBookInitializer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //WordBookInitializer.initializeBook("SmallBook");
        //WordBookInitializer.initializeBook("BEC_2");
//      WordBookInitializer.getBookList();
//      WordBookInitializer.initializeAll();
        UserInterface ui = new UserInterface();
        ui.start();
    }
}
