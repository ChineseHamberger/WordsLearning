package modules.login;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LoginPane extends BorderPane {
    BooleanProperty loginProperty;
    String username;
    public LoginPane()
    {
        UsernameField usernameField = new UsernameField();

        LoginButton loginButton = new LoginButton("Login");
        loginProperty = loginButton.loginProperty;
        loginButton.setOnAction(e->{
            username = usernameField.textField.getText();
            System.out.println(username);
            loginProperty.setValue(true);
        });

        VBox vBox = new VBox(usernameField,loginButton);
        vBox.setSpacing(25);
        this.setMaxSize(500,500);
        vBox.setAlignment(Pos.CENTER);
        this.setCenter(vBox);
    }
    public BooleanProperty loginProperty(){
        return loginProperty;
    }
    public String getUsername(){
        return username;
    }
}