package hotcupsofjava.transitsystemmanager.screens.UserScreen;

import hotcupsofjava.transitsystemmanager.objects.userobjects.Card;
import hotcupsofjava.transitsystemmanager.objects.userobjects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class UserStartScreen {

    public Button editBtn;
    public Button addCardBtn;
    public Text emailText;
    public Text nameText;
    public TableColumn cardIDCol;
    public TableColumn cardNameCol;
    public TableColumn cardBalanceCol;
    public TableView cardsTable;
    private User user;

    public UserStartScreen(User user){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserStartScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.user = user;
        this.nameText.setText(this.user.getName());
        this.emailText.setText(this.user.getEmail());
        initializeTable();
        setCardTable();
    }

    private void initializeTable(){
        cardIDCol.setCellValueFactory(new PropertyValueFactory<Card,String>("id"));
        cardNameCol.setCellValueFactory(new PropertyValueFactory<Card,String>("cardName"));
    }

    private void setCardTable(){
        ObservableList<Card> cards = FXCollections.observableArrayList();
        cards.addAll(user.getCards());
    }
}
