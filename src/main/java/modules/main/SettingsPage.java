   package modules.main;

   import com.almasb.fxgl.app.GameSettings;
   import effects.MyVbox;
   import javafx.scene.control.*;
   import javafx.scene.layout.HBox;
   import javafx.scene.layout.Pane;
   import javafx.scene.layout.VBox;
   import settings.GlobalSetting;
   import ymc.LocalStorage.LocalStorage;

   public class SettingsPage extends Pane {
       private GlobalSetting setting;

       public SettingsPage(GlobalSetting globalSetting) {
           LocalStorage storage = new LocalStorage();
           setting = globalSetting;

           ToggleGroup playUSSpeechFirstGroup = new ToggleGroup();
           RadioButton playUSSpeechFirstRadioButton = new RadioButton("优先播放美式发音");
           RadioButton playUKSpeechFirstRadioButton = new RadioButton("优先播放英式发音");

           playUSSpeechFirstRadioButton.setToggleGroup(playUSSpeechFirstGroup);
           playUKSpeechFirstRadioButton.setToggleGroup(playUSSpeechFirstGroup);

           if (setting.getPlayUSSpeechFirst()) {
               playUSSpeechFirstRadioButton.setSelected(true);
           } else {
               playUKSpeechFirstRadioButton.setSelected(true);
           }

           playUSSpeechFirstGroup.selectedToggleProperty().addListener((obs, oldValue, newValue) -> {
               if (newValue != null){
                   if (playUSSpeechFirstRadioButton.isSelected()) {
                       setting.setUSSpeechFirst(true);
                       System.out.println("Set US Speech First");
                       storage.saveGlobalSettings(setting);
                   } else {
                       setting.setUKSpeechFirst(true);
                       System.out.println("Set UK Speech First");
                       storage.saveGlobalSettings(setting);
                   }
               }
           });

           HBox playUSSpeechFirstHBox = new HBox(10);
           playUSSpeechFirstHBox.getChildren().addAll(playUSSpeechFirstRadioButton, playUKSpeechFirstRadioButton);

           // 添加全屏启动选项
           ToggleGroup fullScreenOnStartGroup = new ToggleGroup();
           RadioButton fullScreenOnStartYButton = new RadioButton("全屏启动");
           RadioButton fullScreenOnStartNButton = new RadioButton("非全屏启动");
           fullScreenOnStartYButton.setToggleGroup(fullScreenOnStartGroup);
           fullScreenOnStartNButton.setToggleGroup(fullScreenOnStartGroup);
           if (setting.getFullScreen()) {
               fullScreenOnStartYButton.setSelected(true);
           }
           else{
               fullScreenOnStartNButton.setSelected(true);
           }
           fullScreenOnStartGroup.selectedToggleProperty().addListener((obs, oldValue, newValue) -> {
               if (newValue != null){
                   if (fullScreenOnStartYButton.isSelected()) {
                       setting.setFullScreen(true);
                       System.out.println("Set Full Screen On Start");
                       storage.saveGlobalSettings(setting);
                   } else {
                       setting.setFullScreen(false);
                       System.out.println("Set Not Full Screen On Start");
                       storage.saveGlobalSettings(setting);
                   }
               }
           });

           HBox fullScreenOnStartHBox = new HBox(10);
           fullScreenOnStartHBox.getChildren().addAll(fullScreenOnStartYButton, fullScreenOnStartNButton);

           MyVbox root = new MyVbox(10);
           root.getChildren().addAll(playUSSpeechFirstHBox);
           root.getChildren().addAll(fullScreenOnStartHBox);

           getChildren().add(root);


           // 创建控件
//           CheckBox enableFeatureCheckbox = new CheckBox("Enable Feature");
//           Slider valueSlider = new Slider(0, 100, globalSetting.getValue());
//
//           // 设置初始值
//           enableFeatureCheckbox.setSelected(globalSetting.isFeatureEnabled());
//           valueSlider.setValue(globalSetting.getValue());
//
//           // 添加事件监听器
//           enableFeatureCheckbox.setOnAction(event -> setting.setFeatureEnabled(enableFeatureCheckbox.isSelected()));
//           valueSlider.valueProperty().addListener((obs, oldValue, newValue) -> setting.setValue((int) newValue.doubleValue()));
//
//           // 布局控件
//           VBox root = new VBox(10);
//           root.getChildren().addAll(enableFeatureCheckbox, valueSlider);
//
//           // 设置SettingsPage的内容
//           setPrefSize(GameSettings.WIDTH * 0.8, GameSettings.HEIGHT * 0.8);
//           getChildren().add(root);
       }
   }
   