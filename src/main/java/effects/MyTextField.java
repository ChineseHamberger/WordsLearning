package effects;

import javafx.scene.control.TextField;

public class MyTextField extends TextField {
    public MyTextField()
    {
        super();
        this.getStyleClass().add("myTextField");
        this.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/Style.css")));
    }
}
