package hotcupsofjava.transitsystemmanager.UserScreen;

import javafx.fxml.FXMLLoader;

public class UserStartScreen {

    public UserStartScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UsStartScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
    }
}
