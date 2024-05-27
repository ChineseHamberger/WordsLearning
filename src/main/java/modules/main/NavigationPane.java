package modules.main;


import effects.SelfDefinedColors;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

public class NavigationPane extends Pane {
    {
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setEffect(new DropShadow(BlurType.GAUSSIAN,Color.rgb(0, 0, 0,0.3),10,0,4,0));
    }
    ArrayList<NavigationButton> navigationBtnList = new ArrayList<>();
    VBox btnBox = new VBox();
    ToggleGroup toggleGroup = new ToggleGroup();
    Rectangle selectBar = new Rectangle(18,60, SelfDefinedColors.LIGHT_BLUE);

    TranslateTransition barTransition = new TranslateTransition(Duration.seconds(0.15),selectBar);
    IntegerProperty isSelectedProperty = new SimpleIntegerProperty(-1);
    public NavigationPane(String... names) {
        this.setPrefSize(240,750);
        for(int i = 0;i < names.length;i++){
            NavigationButton navigationButton = new NavigationButton(names[i]);
            navigationButton.setIndex(i);
            navigationButton.selectedProperty().addListener(((observable, oldValue, newValue) -> {
                if(newValue){
                    isSelectedProperty.set(navigationButton.getIndex());
                }
            }));
            navigationButton.setToggleGroup(toggleGroup);
            btnBox.getChildren().add(navigationButton);
            navigationBtnList.add(navigationButton);
        }
        btnBox.setSpacing(10);
        btnBox.setPadding(new Insets(0,3,0,3));
        selectBar.setTranslateX(-9);
        selectBar.setArcWidth(10);
        selectBar.setArcHeight(10);
        selectBar.setVisible(false);
        barTransition.setInterpolator(Interpolator.EASE_OUT);
        isSelectedProperty.addListener(((observable, oldValue, newValue) -> {
            if(!selectBar.isVisible()) {
                selectBar.setY(navigationBtnList.get(isSelectedProperty.get()).getLayoutY());
                selectBar.setVisible(true);
            } else {
                //下移
                if(oldValue.intValue() - newValue.intValue() < 0){
                    double translateY = -(60*(oldValue.intValue() - newValue.intValue())) + btnBox.getSpacing()*-(oldValue.intValue() - newValue.intValue());
                    barTransition.stop();
                    barTransition.setByY(translateY);
                    barTransition.play();
                    //上移
                }else if(oldValue.intValue() - newValue.intValue() > 0){
                    double translateY = -(60*(oldValue.intValue() - newValue.intValue())) - btnBox.getSpacing()*(oldValue.intValue() - newValue.intValue());
                    barTransition.stop();
                    barTransition.setByY(translateY);
                    barTransition.play();
                }
            }
        }));
        this.getChildren().add(btnBox);
        this.getChildren().add(selectBar);
    }
}
