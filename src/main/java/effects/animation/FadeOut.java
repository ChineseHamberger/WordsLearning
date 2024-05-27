package effects.animation;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * FadeOut类用于创建一个节点淡出的动画效果，包括平移、透明度变化和缩放三个子动画。
 */
public class FadeOut {
    TranslateTransition translateTransition; // 平移过渡效果
    FadeTransition fadeTransition; // 透明度过渡效果
    ScaleTransition scaleTransition; // 缩放过渡效果

    /**
     * 构造函数，初始化淡出动画效果。
     * @param node 要应用动画的节点。
     */
    public FadeOut(Node node) {
        // 初始化平移过渡，使节点向左平移40个单位
        translateTransition = new TranslateTransition(Duration.seconds(0.4),node);
        translateTransition.setByX(-40);

        // 初始化透明度过渡，使节点透明度降低到0
        fadeTransition = new FadeTransition(Duration.seconds(0.4),node);
        fadeTransition.setByValue(-1);

        // 初始化缩放过渡，使节点在x和y方向上缩小10%
        scaleTransition = new ScaleTransition(Duration.seconds(0.4),node);
        scaleTransition.setByY(-0.1);
        scaleTransition.setByX(-0.1);
    }

    /**
     * 播放所有的动画效果。
     */
    public void play(){
        translateTransition.play();
        fadeTransition.play();
        scaleTransition.play();
    }

    /**
     * 设置平移过渡完成后的操作。
     * @return 返回平移过渡对象，可以链式调用设置其他属性。
     */
    public TranslateTransition setOnFinished(){
        return  translateTransition;
    }
}

