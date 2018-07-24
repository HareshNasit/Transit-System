package hotcupsofjava.transitsystemmanager.screens.UserScreen;

import hotcupsofjava.transitsystemmanager.objects.userobjects.Card;
import hotcupsofjava.transitsystemmanager.objects.userobjects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserStartScreen implements Initializable {

    public Button editBtn;
    public Button addCardBtn;
    public Text emailText;
    public Text nameText;
    public TableColumn cardIDCol;
    public TableColumn cardNameCol;
    public TableColumn cardBalanceCol;
    public TableView cardsTable;
    public User user;

//    public UserStartScreen(User user){
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserStartScreen.fxml"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.setController(this);
//        this.user = user;
//        this.nameText.setText(this.user.getName());
//        this.emailText.setText(this.user.getEmail());
//        initializeTable();
//        setCardTable();
//    }

    public void initialize(URL location, ResourceBundle resources){
        cardIDCol.setCellValueFactory(new PropertyValueFactory<Card,String>("id"));
        cardNameCol.setCellValueFactory(new PropertyValueFactory<Card,String>("cardName"));
        cardBalanceCol.setCellValueFactory(new PropertyValueFactory<Card,Double>("balance"));
    }

    public void setCardTable(){
        ObservableList<Card> cards = FXCollections.observableArrayList();
        cards.addAll(user.getCards());
        cardsTable.setItems(cards);
    }

    public void changeName(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit Name");
        dialog.setContentText("New Name");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            user.setName(result.get());
            nameText.setText(result.get());
        }
    }
}
