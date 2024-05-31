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
           RadioButton playUSSpeechFirstRadioButton = new RadioButton("Play US Speech First");
           RadioButton playUKSpeechFirstRadioButton = new RadioButton("Play UK Speech First");

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
                       setting.setUSSpeechFirst();
                       System.out.println("Set US Speech First");
                       storage.saveGlobalSettings(setting);
                   } else {
                       setting.setUKSpeechFirst();
                       System.out.println("Set UK Speech First");
                       storage.saveGlobalSettings(setting);
                   }
               }
           });

           HBox playUSSpeechFirstHBox = new HBox(10);
           playUSSpeechFirstHBox.getChildren().addAll(playUSSpeechFirstRadioButton, playUKSpeechFirstRadioButton);

           MyVbox root = new MyVbox(10);
           root.getChildren().addAll(playUSSpeechFirstHBox);

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
   