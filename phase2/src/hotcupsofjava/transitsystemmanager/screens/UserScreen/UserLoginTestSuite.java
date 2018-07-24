package hotcupsofjava.transitsystemmanager.screens.UserScreen;

import hotcupsofjava.transitsystemmanager.managers.UserManager;
import hotcupsofjava.transitsystemmanager.objects.userobjects.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserLoginTestSuite extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Stage window = new Stage();
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("UserLoginScreen.fxml"));
        Parent root = loader.load();
        UserLoginScreen controller = loader.getController();
        User user = new User("H1","harsh","harshnasit123@gmail.com");
        UserManager userManager = new UserManager();
        userManager.addUser("H2","harsh","harshnasit@gmail.com");
        userManager.addCard(user,"CD1");
        userManager.addCard(user,"CD2");
        controller.userManager = userManager;
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Card screen");
        window.setScene(new Scene(root));
        window.show();
    }
}
