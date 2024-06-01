package modules.main;

import effects.MyLabel;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import tools.AudioKit;
import ymc.translator.Translator;


public class QueryBox extends VBox {

    private ComboBox<String> modeComboBox;
    private TextField queryField;
    private Button translateButton;
    private MyLabel resultLabel;

    public QueryBox() {

        // 初始化UI组件
        MyLabel modeLabel = new MyLabel("选择翻译模式:");

        this.modeComboBox = new ComboBox<>();
        this.modeComboBox.getItems().addAll("中译英", "英译中");
        this.modeComboBox.setValue("英译中");

        MyLabel queryLabel = new MyLabel("输入单词:");

        this.queryField = new TextField();
        this.queryField.setMaxSize(200, 30);

        this.translateButton = new Button("翻译");

        this.resultLabel = new MyLabel();
        this.resultLabel.setWrapText(true);
        resultLabel.setMaxSize(400, 200);

        // 创建一个HBox来放置queryField和translateButton
        HBox queryAndButtonBox = new HBox(10);
        queryAndButtonBox.setAlignment(Pos.CENTER);
        queryAndButtonBox.getChildren().addAll(queryField, translateButton);

        // 设置布局
        setAlignment(Pos.CENTER);
        setSpacing(10);
        getChildren().addAll(modeLabel, modeComboBox, queryLabel, queryAndButtonBox, resultLabel);

        // 添加事件监听器
        translateButton.setOnAction(event -> handleTranslate());
    }

    // ... createButtonBar() 方法已移除，因为不再需要 ...

    private void handleTranslate() {
        int mode = modeComboBox.getSelectionModel().getSelectedIndex() + 1; // 索引调整
        String query = queryField.getText().trim();
        if (mode==2){
            //AudioKit.playUSSpeech(query);
        }
        if (!query.isEmpty()) {
            String result = Translator.translate(mode, query);
            resultLabel.setText(result);
        } else {
            showAlert("错误", "请输入一个单词。", Alert.AlertType.ERROR);
        }
    }

    // ... showAlert() 方法 ...
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

