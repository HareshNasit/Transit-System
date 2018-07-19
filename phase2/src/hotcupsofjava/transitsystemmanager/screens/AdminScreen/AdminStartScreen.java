package hotcupsofjava.transitsystemmanager.screens.AdminScreen;

import javafx.fxml.FXMLLoader;

public class AdminStartScreen {

    public AdminStartScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminStartScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
    }
}
