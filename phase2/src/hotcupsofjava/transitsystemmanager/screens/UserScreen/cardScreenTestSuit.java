package hotcupsofjava.transitsystemmanager.screens.UserScreen;

import hotcupsofjava.transitsystemmanager.managers.UserManager;
import hotcupsofjava.transitsystemmanager.objects.userobjects.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class cardScreenTestSuit extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Stage window = new Stage();
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("CardScreen.fxml"));
        Parent root = loader.load();
        CardScreen controller = loader.getController();
        User user = new User("H1","harsh","harshnasit123@gmail.com");
        UserManager userManager = new UserManager();
//        controller.user = user;
//        controller.nameText.setText(user.getName());
//        controller.emailText.setText(user.getEmail());
        userManager.addCard(user,"CD1");
        userManager.addCard(user,"CD2");
//        controller.setCardTable();
        controller.userManager = userManager;
        controller.card = userManager.getCard("CD1");
        controller.cardID.setText(controller.card.getId());
        controller.cardName.setText(controller.card.getCardName());
        controller.tab1Balance.setText(Double.toString(controller.card.getBalance()));
        controller.cardName.setText("card1");
        controller.tab3Balance.setText(Double.toString(controller.card.getBalance()));
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Card screen");
        window.setScene(new Scene(root));
        window.show();
    }
}
