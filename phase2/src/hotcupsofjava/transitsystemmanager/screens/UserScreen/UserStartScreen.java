package hotcupsofjava.transitsystemmanager.screens.UserScreen;

import javafx.fxml.FXMLLoader;

public class UserStartScreen {

    public UserStartScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserStartScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
    }
}
