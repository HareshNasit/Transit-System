package hotcupsofjava.transitsystemmanager.screens.UserScreen;

import hotcupsofjava.transitsystemmanager.managers.RouteManager;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardScreen implements Initializable {

    public Text cardName;
    public Text cardID;
    public Text tab1Balance;
    public Button suspendCardBtn;
    public Button removeCardBtn;
    public TableView subwayTable;
    public TableColumn subwayNameCol;
    public TextField tab3BalenceInput;
    public Text tab3Balance;
    public Tab tab3;
    public Button tapOffBtn;
    public Button tapOnBtn;
    public TableColumn subwayLineCol;
    public TableView busStopTable;
    public TableColumn BusStopNameCol;
    public TableColumn BusRouteCol;
    public Tab tab2;
    public Tab tab1;
    private RouteManager routeManager;


//    public CardScreen(){
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserStartScreen.fxml"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.setController(this);
//        try {
//            fxmlLoader.load();
//        }
//        catch (IOException e){
//            throw new RuntimeException();
//        }
//    }

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("hii");
    }
}
