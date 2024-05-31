package effects;

import javafx.scene.layout.VBox;

public class MyVbox extends VBox {
    public MyVbox()
    {
        super();
        this.getStyleClass().add("myVbox");
        this.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/Style.css")));
    }
    public MyVbox(double spacing){
        super(spacing);
        this.getStyleClass().add("myVbox");
        this.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/Style.css")));
    }
}
