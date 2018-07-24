package hotcupsofjava.transitsystemmanager.screens.UserScreen;

import hotcupsofjava.transitsystemmanager.managers.RouteManager;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.BusStop;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.Station;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
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
    public TextField tab3BalanceInput;
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
    public Button addAmountBtn;
    public Label tab3resultLbl;
    private RouteManager routeManager;
    public Card card;


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
        subwayNameCol.setCellValueFactory(new PropertyValueFactory<Station,String>("name"));
        BusStopNameCol.setCellValueFactory(new PropertyValueFactory<BusStop,String>("name"));
        //TODO: ADD subwayline and busstop route.
    }

    public void setStationTable(){
        ObservableList<Station> stations = FXCollections.observableArrayList();
        stations.addAll(routeManager.getStations().values());
        subwayTable.setItems(stations);
    }

    public void setStopsTable(){
        ObservableList<BusStop> stops = FXCollections.observableArrayList();
        stops.addAll(routeManager.getStops().values());
        busStopTable.setItems(stops);
    }
    public void addBalance(ActionEvent actionEvent) {
        String amnt =tab3BalanceInput.getText();
        if (amnt.equals("10") || amnt.equals("20") || amnt.equals("50")) {
            int amount = Integer.parseInt(tab3BalanceInput.getText());
            card.addBalance(amount);
            updateBalance();
            tab3resultLbl.setText("Balance of " + amount +" added successfully" );
            tab3resultLbl.setTextFill(Color.valueOf("Green"));
        }
        else{
            tab3resultLbl.setText("Please enter a valid amount");
            tab3resultLbl.setTextFill(Color.valueOf("Red"));
        }
    }
    public void updateBalance(){
        tab1Balance.setText(Double.toString(card.getBalance()));
        tab3Balance.setText(Double.toString(card.getBalance()));
    }
}
