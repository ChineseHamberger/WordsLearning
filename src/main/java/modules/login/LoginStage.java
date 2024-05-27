package modules.login;

import javafx.beans.property.BooleanProperty;
import javafx.stage.Stage;

public class LoginStage extends Stage {
    BooleanProperty isOver;

    public LoginStage(int width,int height){
        setTitle("Login");
        LoginPane loginPane = new LoginPane();

    }
}
