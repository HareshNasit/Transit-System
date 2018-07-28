package hotcupsofjava.transitsystemmanager.screens.UserScreen;

import hotcupsofjava.transitsystemmanager.managers.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserLoginScreen extends AnchorPane {
    public Button loginBtn;
    public Label resultLabel;
    public TextField emailText;
    public TextField nameText;
    public UserManager userManager;

    public UserLoginScreen(UserManager userManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserLoginScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            this.userManager = userManager;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
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
}
