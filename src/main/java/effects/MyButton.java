package effects;

import javafx.scene.control.Button;

public class MyButton extends Button {
    public MyButton()
    {
        super();
        this.getStyleClass().add("myButton");
        this.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/Style.css")));
    }
    public MyButton(String text)
    {
        super(text);
        this.getStyleClass().add("myButton");
        this.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/Style.css")));
    }
}
