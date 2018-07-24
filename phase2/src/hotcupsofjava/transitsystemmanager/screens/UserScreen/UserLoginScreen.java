package hotcupsofjava.transitsystemmanager.screens.UserScreen;

import hotcupsofjava.transitsystemmanager.managers.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class UserLoginScreen implements Initializable {
    public Button loginBtn;
    public Label resultLabel;
    public TextField emailText;
    public TextField nameText;
    public UserManager userManager;

    public void checkLogin(ActionEvent actionEvent) {
        String name = nameText.getText();
        String email = emailText.getText();
        if(userManager.hasUser(name,email)){
            resultLabel.setText("Login Successful");
            resultLabel.setTextFill(Color.valueOf("Green"));
        }
        else{
            resultLabel.setText("Wrong Credentials");
            resultLabel.setTextFill(Color.valueOf("Red"));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
