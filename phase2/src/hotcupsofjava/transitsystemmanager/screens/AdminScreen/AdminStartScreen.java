package hotcupsofjava.transitsystemmanager.screens.AdminScreen;

import hotcupsofjava.transitsystemmanager.Logger;
import hotcupsofjava.transitsystemmanager.managers.RouteManager;
import hotcupsofjava.transitsystemmanager.managers.UserManager;
import hotcupsofjava.transitsystemmanager.objects.userobjects.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class AdminStartScreen extends VBox{

    private UserManager userManager;
    private RouteManager routeManager;
    public Button startDayBtn;
    public Button endDayBtn;

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

    public void startDay(javafx.event.ActionEvent actionEvent) {
        Logger.startDay("1");
    }

    public void endDay(javafx.event.ActionEvent actionEvent) {
    }
}
