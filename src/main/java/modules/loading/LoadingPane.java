package modules.loading;

import com.almasb.fxgl.core.UpdatableRunner;
import effects.SelfDefinedColors;
import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.BorderPane;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Objects;

public class LoadingPane extends BorderPane {
    public LoadingPane() {
        this.setMaxSize(100,100);
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Loading.png"))));
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), imageView);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();
        Label label = new Label("Loading...");
        label.setTextFill(SelfDefinedColors.GRAY);
        VBox vBox = new VBox(imageView,label);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        this.setCenter(vBox);
    }

}
