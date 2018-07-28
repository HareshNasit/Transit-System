package hotcupsofjava.transitsystemmanager.screens.UserScreen;

import hotcupsofjava.transitsystemmanager.managers.RouteManager;
import hotcupsofjava.transitsystemmanager.managers.UserManager;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.BusStop;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.Station;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Card;
import hotcupsofjava.transitsystemmanager.objects.userobjects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
    public Button add10Btn;
    public Button add20Btn;
    public Button add50Btn;
    private RouteManager routeManager;
    public UserManager userManager;
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
        if (amnt.equals("")){
            tab3resultLbl.setText("Please enter a valid amount");
            tab3resultLbl.setTextFill(Color.valueOf("Red"));
        }
        else {
            try {
                int amount = Integer.parseInt(tab3BalanceInput.getText());
                card.getUser().loadCard(card,amount);
                updateBalance();
                tab3resultLbl.setText("Balance of " + amount + " added successfully");
                tab3resultLbl.setTextFill(Color.valueOf("Green"));
            }
            catch (RuntimeException e){
                tab3resultLbl.setText("Please enter a valid amount");
                tab3resultLbl.setTextFill(Color.valueOf("Red"));
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

    public void addBalanceTwenty(ActionEvent actionEvent) {
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

    public void addBalanceFifty(ActionEvent actionEvent) {
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
