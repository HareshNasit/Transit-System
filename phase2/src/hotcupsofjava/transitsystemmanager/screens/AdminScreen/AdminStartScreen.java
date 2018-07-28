package hotcupsofjava.transitsystemmanager.screens.AdminScreen;

import hotcupsofjava.transitsystemmanager.managers.RouteManager;
import hotcupsofjava.transitsystemmanager.managers.UserManager;
import hotcupsofjava.transitsystemmanager.objects.userobjects.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AdminStartScreen extends VBox{

    private UserManager userManager;
    private RouteManager routeManager;

    public AdminStartScreen(UserManager userManager, RouteManager routeManager){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminStartScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.routeManager = routeManager;
            this.userManager = userManager;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
