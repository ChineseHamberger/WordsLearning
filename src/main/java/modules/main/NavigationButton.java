package modules.main;


import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class NavigationButton extends BorderPane {
    static Color label_default = Color.rgb(67,67,67);
    static Color label_hover = Color.rgb(24,24,24);

    static Color label_selected = Color.rgb(13,144,238);
    static Color btn_default = Color.WHITE;
    static Color btn_hover = Color.rgb(237,237,237);
    Rectangle rectangle = new Rectangle();
    Label label;
    ToggleButton toggleButton = new ToggleButton();

    private final FillTransition btnTransition1;
    private final FillTransition btnTransition2;

    private final Timeline labelTransition1;
    private final Timeline labelTransition2;

    private final Timeline labelTransition3;
    private final Timeline labelTransition4;
    private int index;


    public NavigationButton(String string) {
        this.setMaxSize(232,60);
        this.setPrefSize(232,60);
        label = new Label(string);
        label.setTextFill(label_default);
        label.setFont(Font.font("Microsoft YaHei UI Light",FontWeight.NORMAL,28));
        rectangle = new Rectangle(232,60,btn_default);
        rectangle.setArcWidth(10);rectangle.setArcHeight(10);
        BorderPane align = new BorderPane();
        align.setPrefSize(232,60);
        align.setCenter(label);
        Pane innerPane = new Pane(rectangle,align);
        innerPane.setPrefSize(232,60);
        btnTransition1 = new FillTransition(Duration.seconds(0.2),rectangle,btn_default, btn_hover);
        btnTransition2 = new FillTransition(Duration.seconds(0.2),rectangle, btn_hover,btn_default);
        labelTransition1 = new Timeline(new KeyFrame(Duration.seconds(0.2),
                new KeyValue(label.textFillProperty(), label_hover)));
        labelTransition2 = new Timeline(new KeyFrame(Duration.seconds(0.2),
                new KeyValue(label.textFillProperty(),label_default)));
        labelTransition3 = new Timeline(new KeyFrame(Duration.seconds(0.15),
                new KeyValue(label.textFillProperty(),label_selected)));
        labelTransition4 = new Timeline(new KeyFrame(Duration.seconds(0.15),
                new KeyValue(label.textFillProperty(),label_default)));

        this.addEventHandler(MouseEvent.MOUSE_ENTERED,event -> {
            mouseEnter();
        });
        this.addEventHandler(MouseEvent.MOUSE_EXITED,event -> {
            mouseExit();
        });
        this.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            if(this.toggleButton.isSelected()){
                toggleButton.setSelected(!toggleButton.isSelected());
            }else{
                toggleButton.setSelected(!toggleButton.isSelected());
            }
        });
        toggleButton.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue){
                labelTransition3.play();
            }else {
                labelTransition4.play();
            }
        }));
        this.setCenter(innerPane);
    }

    public void setToggleGroup(ToggleGroup toggleGroup) {
        this.toggleButton.setToggleGroup(toggleGroup);
    }

    public void mouseEnter(){
        btnTransition1.stop();
        labelTransition1.stop();
        if(!toggleButton.isSelected()) labelTransition1.play();
        btnTransition1.play();
        setCursor(Cursor.HAND);
    }

    public void mouseExit(){
        btnTransition2.stop();
        labelTransition2.stop();
        if(!toggleButton.isSelected()) labelTransition2.play();
        btnTransition2.play();
        setCursor(Cursor.DEFAULT);
    }

    public void stopTransitions(){
        btnTransition1.stop();
        btnTransition2.stop();
        labelTransition1.stop();
        labelTransition2.stop();
    }

    public BooleanProperty selectedProperty(){
        return this.toggleButton.selectedProperty();
    }

    public void setSelect(boolean b){
        this.toggleButton.setSelected(b);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
