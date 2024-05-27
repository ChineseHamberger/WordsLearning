package effects.animation;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * FadeIn类用于创建一个节点淡入的动画效果。
 * 该效果包括节点的平移、透明度变化和缩放。
 */
public class FadeIn {
    TranslateTransition translateTransition; // 平移过渡效果
    FadeTransition fadeTransition; // 透明度过渡效果
    ScaleTransition scaleTransition; // 缩放过渡效果

    /**
     * 构造函数，初始化淡入效果的三个组件：平移、透明度和缩放。
     * 设置各个过渡的属性，如持续时间、起始和结束值。
     *
     * @param node 要应用动画的JavaFX节点
     */
    public FadeIn(Node node) {
        // 平移过渡
        translateTransition = new TranslateTransition(Duration.seconds(0.4),node); // 初始化平移过渡，将节点从X轴40位置移动到0位置
        translateTransition.setFromX(40);
        translateTransition.setToX(0);
        // 透明度过渡
        fadeTransition = new FadeTransition(Duration.seconds(0.4),node); // 初始化透明度过渡，从完全透明（0）变到完全不透明（1）
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        // 缩放过渡
        scaleTransition = new ScaleTransition(Duration.seconds(0.4),node); // 初始化缩放过渡，从缩小的状态（0.9）放大到正常大小（1）
        scaleTransition.setFromX(0.9);
        scaleTransition.setFromY(0.9);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
    }

    /**
     * 播放淡入动画，同时开始平移、透明度和缩放的过渡效果。
     */
    public void play(){
        translateTransition.play(); // 播放平移过渡
        fadeTransition.play(); // 播放透明度过渡
        scaleTransition.play(); // 播放缩放过渡
    }
}

