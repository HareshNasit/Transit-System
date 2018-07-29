package hotcupsofjava.transitsystemmanager;

import hotcupsofjava.transitsystemmanager.managers.IDManager;
import hotcupsofjava.transitsystemmanager.managers.RouteManager;
import hotcupsofjava.transitsystemmanager.managers.UserManager;
import hotcupsofjava.transitsystemmanager.objects.TransitSystemObject;
import hotcupsofjava.transitsystemmanager.screens.AdminScreen.AdminStartScreen;
import hotcupsofjava.transitsystemmanager.screens.UserScreen.UserLoginScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainSystem extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        IDManager idManager = new IDManager();
        TransitSystemObject.setIdManager(idManager);
        UserManager userManager = new UserManager();
        RouteManager routeManager = new RouteManager();

        Stage loginWindow = new Stage();
        UserLoginScreen loginController = new UserLoginScreen(userManager,routeManager);
        loginWindow.initModality(Modality.WINDOW_MODAL);
        loginWindow.setTitle("Login Screen");
        loginWindow.setScene(new Scene(loginController));
        loginWindow.show();

        Stage adminWindow = new Stage();
        AdminStartScreen adminController = new AdminStartScreen(userManager,routeManager);
        adminWindow.initModality(Modality.WINDOW_MODAL);
        adminWindow.setTitle("Admin Screen");
        adminWindow.setScene(new Scene(adminController));
        adminWindow.show();

        userManager.addUser("H1","harsh","harshnasit123@gmail.com");
    }
}
