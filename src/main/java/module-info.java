module org.example.wordslearning {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.jsoup;
    requires java.desktop;
    requires java.net.http;
    requires org.json;

    opens org.example.wordslearning to javafx.fxml;
    exports org.example.wordslearning;
}