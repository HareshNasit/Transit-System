package hotcupsofjava.transitsystemmanager.screens.UserScreen;

import hotcupsofjava.transitsystemmanager.managers.RouteManager;
import hotcupsofjava.transitsystemmanager.managers.UserManager;
import hotcupsofjava.transitsystemmanager.objects.userobjects.User;
import hotcupsofjava.transitsystemmanager.screens.AdminScreen.AdminStartScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserLoginScreen extends AnchorPane {
    public Button loginBtn;
    public Label resultLabel;
    public TextField idText;
    public TextField nameText;
    public Button signUpBtn;
    private UserManager userManager;
    private RouteManager routeManager;

    public UserLoginScreen(UserManager userManager, RouteManager routeManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserLoginScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            this.userManager = userManager;
            this.routeManager = routeManager;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    public void checkLogin(ActionEvent actionEvent) {
        String id = idText.getText();
        if(userManager.hasUser(id)){
            resultLabel.setText("Login Successful");
            resultLabel.setTextFill(Color.valueOf("Green"));
            User user = userManager.getUser(id);
            Stage userStartScreen = new Stage();
            UserStartScreen userStartScreenController = new UserStartScreen(user,userManager,routeManager);
            userStartScreen.initModality(Modality.WINDOW_MODAL);
            userStartScreen.setTitle("User Screen");
            userStartScreen.setScene(new Scene(userStartScreenController));
            userStartScreen.show();
        }
        else{
            resultLabel.setText("Wrong Credentials");
            resultLabel.setTextFill(Color.valueOf("Red"));
        }
    }

    public void createNewUser(ActionEvent actionEvent) {

        Stage signUpScreen = new Stage();
        UserSignUpScreen userSignUpScreen = new UserSignUpScreen(userManager, routeManager);
        signUpScreen.initModality(Modality.WINDOW_MODAL);
        signUpScreen.setTitle("Sign Up");
        signUpScreen.setScene(new Scene(userSignUpScreen));
        signUpScreen.show();
        Stage loginScreen = (Stage) signUpBtn.getScene().getWindow();
        loginScreen.close();
//        Stage loginScreen = (Stage) signUpBtn.getScene().getWindow();
//        Stage stage = new Stage();
//        Scene loginScene = loginScreen.getScene();
//        Text name = new Text();
//        Text email = new Text();
//        Text password = new Text();
//        TextField inputName = new TextField();
//        inputName.setLayoutX(169);
//        inputName.setLayoutY(97);
//        TextField inputEmail = new TextField();
//        inputEmail.setLayoutX(169);
//        inputEmail.setLayoutY(142);
//        TextField inputPassword = new TextField();
//        inputPassword.setLayoutX(169);
//        inputPassword.setLayoutY(187);
//        Button create = new Button();
//        VBox signUp = new VBox(20);
//        Scene scene = new Scene(signUp,800,800);
//        loginScreen.setScene(scene);
//        signUp.getChildren().add(create);
//        signUp.getChildren().add(name);
//        signUp.getChildren().add(email);
//        signUp.getChildren().add(password);
//        signUp.getChildren().add(inputEmail);
//        signUp.getChildren().add(inputName);
//        signUp.getChildren().add(inputPassword)
//        ;
    }
}
