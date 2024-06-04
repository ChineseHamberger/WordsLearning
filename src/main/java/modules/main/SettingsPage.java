   package modules.main;

   import effects.MyButton;
   import effects.MyVbox;
   import javafx.scene.control.*;
   import javafx.scene.layout.BorderPane;
   import javafx.scene.layout.HBox;

   import modules.config.ConfigPage;
   import settings.GlobalSetting;
   import tools.FileKit;
   import ymc.LocalStorage.LocalStorage;

   import java.io.File;

   public class SettingsPage extends BorderPane {
       private GlobalSetting setting;

       public SettingsPage(String username, GlobalSetting globalSetting) {
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

           HBox clearCacheHBox = new HBox(10);
           MyButton clearCacheButton = new MyButton("清除数据缓存(不会删除用户数据)");
           clearCacheButton.setOnAction(event -> {
               File booksDir = new File("wordBooks");
               FileKit.clearAllFiles(booksDir);
               File articleDir = new File("articles");
               FileKit.clearAllFiles(articleDir);
           });
           clearCacheHBox.getChildren().add(clearCacheButton);

           HBox configHBox = new HBox(10);
           MyButton configButton = new MyButton("重新选择该用户配置");
           ConfigPage configPage = new ConfigPage(username);
           configHBox.getChildren().add(configButton);

           HBox logHBox = new HBox(10);
           MyButton logButton = new MyButton("查看更新日志");
           LogPage logPage = new LogPage(new File("src/main/resources/log.txt"));
           logHBox.getChildren().add(logButton);
           
           MyVbox root = new MyVbox(10);
           root.getChildren().addAll(playUSSpeechFirstHBox);
           root.getChildren().addAll(fullScreenOnStartHBox);
           root.getChildren().addAll(clearCacheHBox);
           root.getChildren().addAll(configHBox);
           root.getChildren().addAll(logHBox);

           configButton.setOnAction(event -> {
               this.setCenter(configPage);
           });
           configPage.isOverProperty().addListener((obs, oldValue, newValue) -> {
               if (newValue) {
                   this.setCenter(root);
                   configPage.isOverProperty().setValue(false);
               }
           });

           logButton.setOnAction(event -> {
               this.setCenter(logPage);
           });
           logPage.isOverProperty().addListener((obs, oldValue, newValue) -> {
               if (newValue) {
                   this.setCenter(root);
                   logPage.isOverProperty().setValue(false);
               }
           });

           this.setCenter(root);
       }
   }
   