package hotcupsofjava.transitsystemmanager.screens.UserScreen;

import hotcupsofjava.transitsystemmanager.managers.RouteManager;
import hotcupsofjava.transitsystemmanager.managers.UserManager;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.BusStop;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.Station;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.Stop;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.TapSubway;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Card;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Trip;
import hotcupsofjava.transitsystemmanager.objects.userobjects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;

public class CardScreen extends VBox {

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
    public Button add10Btn;
    public Button add20Btn;
    public Button add50Btn;
    public Button tapOnSubwayBtn;
    public Button tapOffSubwayBtn;
    public Button tapOnBusBtn;
    public Button tapOffBusBtn;
    public Button specialBtn;
    public TextArea tripsText;
    private RouteManager routeManager;
    private UserManager userManager;
    private Card card;


    public CardScreen(Card card, UserManager userManager, RouteManager routeManager){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CardScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            initializeStops();
            this.card = card;
            cardName.setText(card.getCardName());
            cardID.setText(card.getId());
            this.routeManager = routeManager;
            this.userManager = userManager;
            setStationTable();
            setStopsTable();
            updateBalance();

        }
        catch (IOException e){
            throw new RuntimeException();
        }
    }

    public void initializeStops() {
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

    public void updateTrips(){
        Trip[] trips = card.getTrips();
        tripsText.setText(trips[0].toString());
    }

    public void addBalance(ActionEvent actionEvent) {
        if(card.isSuspended()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Suspended Card");
            alert.setContentText("Warning! This card is suspended");
            alert.showAndWait();
        }
        else {
            String amnt = tab3BalanceInput.getText();
            if (amnt.equals("")) {
                tab3resultLbl.setText("Please enter a valid amount");
                tab3resultLbl.setTextFill(Color.valueOf("Red"));
            }
            else {
                try {
                    int amount = Integer.parseInt(tab3BalanceInput.getText());
                    card.getUser().loadCard(card, amount);
                    updateBalance();
                    tab3resultLbl.setText("Balance of " + amount + " added successfully");
                    tab3resultLbl.setTextFill(Color.valueOf("Green"));
                } catch (RuntimeException e) {
                    tab3resultLbl.setText("Please enter a valid amount");
                    tab3resultLbl.setTextFill(Color.valueOf("Red"));
                }
            }
        }

    }
    public void updateBalance(){
        tab1Balance.setText(Double.toString(card.getBalance()));
        tab3Balance.setText(Double.toString(card.getBalance()));
    }

    public void suspendCard(ActionEvent actionEvent) {
        if(card.getBalance() >0 && card.getUser().getCards().size() > 1) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Suspend Card");
            dialog.setContentText("Enter the card number you want to transfer this card's balance to");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && userManager.hasCard(result.get())) {
                userManager.getCard(result.get()).addBalance(card.getBalance());
                card.getUser().suspendCard(card);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("You do not have any card with such a card id");
                alert.setContentText("Please select a card with valid id");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to suspend this card?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                card.getUser().suspendCard(card);
            }
        }
    }

    public void removeCard(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure? This may delete all the information" +
                " from this card.",
                ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            User user = card.getUser();
            userManager.removeCard(card, user);
            Stage stage = (Stage) removeCardBtn.getScene().getWindow();
            stage.close();
        }
    }

    public void addBalanceTen(ActionEvent actionEvent) {
        if(card.isSuspended()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Suspended Card");
            alert.setContentText("Warning! This card is suspended");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add $10",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                card.getUser().loadCard(card, 10);
                tab3resultLbl.setText("Balance of " + 10 + " added successfully");
                tab3resultLbl.setTextFill(Color.valueOf("Green"));
                updateBalance();
            }
        }
    }

    public void addBalanceTwenty(ActionEvent actionEvent) {
        if(card.isSuspended()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Suspended Card");
            alert.setContentText("Warning! This card is suspended");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add $20",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                card.getUser().loadCard(card, 20);
                tab3resultLbl.setText("Balance of " + 20 + " added successfully");
                tab3resultLbl.setTextFill(Color.valueOf("Green"));
                updateBalance();
            }
        }
    }

    public void addBalanceFifty(ActionEvent actionEvent) {
        if(card.isSuspended()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Suspended Card");
            alert.setContentText("Warning! This card is suspended");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add $50",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                card.getUser().loadCard(card, 50);
                tab3resultLbl.setText("Balance of " + 50 + " added successfully");
                tab3resultLbl.setTextFill(Color.valueOf("Green"));
                updateBalance();
            }
        }
    }

    public void tapOnSubway(ActionEvent actionEvent) {
        try{
            Station station = (Station) subwayTable.getSelectionModel().getSelectedItem();
            Calendar cal = Calendar.getInstance();
            long minutes = cal.get(Calendar.HOUR)*60 + cal.get(Calendar.MINUTE);
            //card.tapOn(minutes,station);
            TapSubway.getInstance().tapOn(minutes,(Stop) station,card);
        }
        catch (NullPointerException e){
            System.out.println("");
        }
    }

    public void tapOffSubway(ActionEvent actionEvent) {
        try{
            Station station = (Station) subwayTable.getSelectionModel().getSelectedItem();
            Calendar cal = Calendar.getInstance();
            long minutes = cal.get(Calendar.HOUR)*60 + cal.get(Calendar.MINUTE);
            //card.tapOff(minutes,station);
            TapSubway.getInstance().tapOff(minutes,(Stop) station,card);
            updateBalance();
            updateTrips();
        }
        catch (NullPointerException e){
            System.out.println("");
        }
    }

    public void tapOnBus(ActionEvent actionEvent) {
        try{
            BusStop busStop = (BusStop) busStopTable.getSelectionModel().getSelectedItem();
            Calendar cal = Calendar.getInstance();
            long minutes = cal.get(Calendar.HOUR)*60 + cal.get(Calendar.MINUTE);
            card.tapOn(minutes,busStop,routeManager.getRoute(busStop.getRouteID()));
            updateBalance();
        }
        catch (NullPointerException e){
            System.out.println("");
        }
    }

    public void tapOffBus(ActionEvent actionEvent) {
        try{
            BusStop busStop = (BusStop) busStopTable.getSelectionModel().getSelectedItem();
            Calendar cal = Calendar.getInstance();
            long minutes = cal.get(Calendar.HOUR)*60 + cal.get(Calendar.MINUTE);
            card.tapOff(minutes,busStop,routeManager.getRoute(busStop.getRouteID()));
        }
        catch (NullPointerException e){
            System.out.println("");
        }
    }

    public void accelerateTime(ActionEvent actionEvent) {
        try{

        }
        catch (NullPointerException e){
            System.out.println("");
        }
    }
}
