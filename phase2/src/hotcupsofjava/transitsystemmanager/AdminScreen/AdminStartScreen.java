package hotcupsofjava.transitsystemmanager.AdminScreen;

import javafx.fxml.FXMLLoader;

public class AdminStartScreen {

    public AdminStartScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdStartScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
    }
}
