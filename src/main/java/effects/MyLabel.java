package effects;

import javafx.scene.control.Label;

public class MyLabel extends Label {
    public MyLabel() {
        this.getStyleClass().add("myLabel");
        this.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/Style.css")));
    }
    public MyLabel(String text) {
        super(text);
        this.getStyleClass().add("myLabel");
        this.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/Style.css")));
    }
}
