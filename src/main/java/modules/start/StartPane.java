package modules.start;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class StartPane extends BorderPane {
    // 表示开始按钮状态的布尔属性
    BooleanProperty startProperty = new SimpleBooleanProperty(false);

    public StartPane() {
        // 图片路径假设为相对于项目资源目录，根据实际情况调整
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/start.png"))));

        imageView.setFitWidth(600); // 根据需要调整宽度
        imageView.setFitHeight(400); // 根据需要调整高度

        // 创建开始按钮并设置其文本为"Start"
        StartButton startButton = new StartButton("开始你的单词学习之旅!");
        startButton.setMaxSize(300, 50);


        // 设置按钮点击事件，当点击按钮时，startProperty的值变为true
        startButton.setOnAction(event -> {
            startProperty.setValue(true);
        });

        // 将图片和开始按钮都加入到VBox中，设置组件间的间距
        VBox vBox = new VBox(imageView, startButton);
        vBox.setSpacing(10);

        // 设置VBox在面板中的位置为居中
        vBox.setAlignment(Pos.CENTER);

        // 设置面板的最大大小
        this.setMaxSize(500, 500);

        // 将VBox设置为面板的中心组件
        this.setCenter(vBox);
    }

    public BooleanProperty startProperty() {
        return startProperty;
    }
}
