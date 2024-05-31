package modules.start;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * StartPane 是一个位于边框面板中的起始界面组件。
 * 它主要包含一个开始按钮，并提供一个布尔属性来表示是否开始了某些操作。
 */
public class StartPane extends BorderPane {
    // 表示开始按钮状态的布尔属性
    BooleanProperty startProperty;

    /**
     * StartPane 的构造函数，初始化起始面板。
     * 创建一个开始按钮，并将其绑定到startProperty上。
     * 当按钮被点击时，startProperty的值会被设置为true。
     */
    public StartPane(){
        // 创建开始按钮并设置其文本为"Start"
        StartButton startButton = new StartButton("开始你的单词学习之旅!");
        startButton.setMaxSize(300,50);
        // 初始化startProperty为开始按钮的startProperty
        startProperty = startButton.startProperty;
        // 设置按钮点击事件，当点击按钮时，startProperty的值变为true
        startButton.setOnAction(event -> {
            startProperty.setValue(true);
        });

        // 将开始按钮放置在VBox中，设置按钮间的间距
        VBox vBox = new VBox(startButton);
        vBox.setSpacing(10);
        // 设置面板的最大大小
        this.setMaxSize(500,500);
        // 设置VBox在面板中的位置为居中
        vBox.setAlignment(Pos.CENTER);
        // 将VBox设置为面板的中心组件
        this.setCenter(vBox);
    }

    /**
     * 获取开始按钮的状态属性。
     * @return 返回一个布尔属性，表示开始操作的状态。
     */
    public BooleanProperty startProperty() {
        return startProperty;
    }
}


