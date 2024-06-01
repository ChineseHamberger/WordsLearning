package modules.main;

import com.almasb.fxgl.core.UpdatableRunner;
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
import java.util.List;

/**
 * 自定义的导航面板类，用于创建包含多个导航按钮的面板。
 * 面板会根据用户选择的按钮，通过一个选择条来视觉上表示当前选中的导航项。
 */
public class NavigationPane extends Pane {
    // 初始化面板背景和阴影效果
    {
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setEffect(new DropShadow(BlurType.GAUSSIAN,Color.rgb(0, 0, 0,0.3),10,0,4,0));
    }
    // 存储导航按钮的列表
    ArrayList<NavigationButton> navigationBtnList = new ArrayList<>();
    // 用于存放导航按钮的VBox容器
    VBox btnBox = new VBox();
    // 用于按钮互斥的ToggleGroup
    ToggleGroup toggleGroup = new ToggleGroup();
    // 选中条，用于视觉上表示当前选中的按钮


    // 选中条的移动动画

    // 存储当前被选中按钮的索引
    IntegerProperty isSelectedProperty = new SimpleIntegerProperty(-1);

    /**
     * NavigationPane构造函数，用于初始化导航面板，包括添加导航按钮和设置选中条效果。
     *
     * @param names 可变参数，用于指定导航按钮的文本。
     */
    public NavigationPane(double width, double height, List<String> names,boolean isMenu) {
        this.setPrefSize(width,height);
        int size = names.size();

        double blockHeight = (height-10*size)/size;
        if(isMenu){
            blockHeight = 60;
        }
        // 遍历所有传入的名称，创建并添加导航按钮
        for(int i = 0;i < size;i++){
            NavigationButton navigationButton = new NavigationButton(width, blockHeight,names.get(i));
            navigationButton.setIndex(i);
            // 监听按钮选中状态的变化，更新选中条位置
            navigationButton.selectedProperty().addListener(((observable, oldValue, newValue) -> {
                if(newValue){
                    System.out.println("Now selected " + navigationButton.getIndex());
                    isSelectedProperty.set(navigationButton.getIndex());
                }
            }));
            navigationButton.setToggleGroup(toggleGroup);
            btnBox.getChildren().add(navigationButton);
            navigationBtnList.add(navigationButton);
        }
        Rectangle selectBar = new Rectangle(18,blockHeight, SelfDefinedColors.LIGHT_BLUE);
        TranslateTransition barTransition = new TranslateTransition(Duration.seconds(0.15),selectBar);
        // 设置按钮容器的间距和内边距
        btnBox.setSpacing(10);
        btnBox.setPadding(new Insets(0,3,0,3));
        // 初始化选中条的外观和位置
        selectBar.setTranslateX(-9);
        selectBar.setArcWidth(10);
        selectBar.setArcHeight(10);
        selectBar.setVisible(false);
        // 设置选中条移动的缓动函数
        barTransition.setInterpolator(Interpolator.EASE_OUT);
        // 监听当前选中项的变化，以更新选中条的位置
        double finalBlockHeight = blockHeight;
        isSelectedProperty.addListener(((observable, oldValue, newValue) -> {
            if(!selectBar.isVisible()) {
                selectBar.setY(navigationBtnList.get(isSelectedProperty.get()).getLayoutY());
                selectBar.setVisible(true);
            } else {
                // 根据选中项的变化，计算并执行选中条的上移或下移动画
                if(oldValue.intValue() - newValue.intValue() < 0){
                    double translateY = -(finalBlockHeight *(oldValue.intValue() - newValue.intValue())) + btnBox.getSpacing()*-(oldValue.intValue() - newValue.intValue());
                    barTransition.stop();
                    barTransition.setByY(translateY);
                    barTransition.play();
                }else if(oldValue.intValue() - newValue.intValue() > 0){
                    double translateY = -(finalBlockHeight *(oldValue.intValue() - newValue.intValue())) - btnBox.getSpacing()*(oldValue.intValue() - newValue.intValue());
                    barTransition.stop();
                    barTransition.setByY(translateY);
                    barTransition.play();
                }
            }
        }));
        // 将按钮容器和选中条添加到导航面板中
        this.getChildren().add(btnBox);
        this.getChildren().add(selectBar);
    }

    public IntegerProperty getSelectedProperty() {
        return isSelectedProperty;
    }
    public int size()
    {
        return navigationBtnList.size();
    }

}
