package hotcupsofjava.transitsystemmanager.screens.AdminScreen;

import hotcupsofjava.transitsystemmanager.Logger;
import hotcupsofjava.transitsystemmanager.managers.RouteManager;
import hotcupsofjava.transitsystemmanager.managers.UserManager;
import hotcupsofjava.transitsystemmanager.objects.userobjects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class AdminStartScreen extends VBox{

    private UserManager userManager;
    private RouteManager routeManager;
    public Button startDayBtn;
    public Button endDayBtn;
    
    public Button genLogBtn;
    public Button refreshBtn;
    public Button readLogBtn;
    public ComboBox<String> logCbx;
    public TextArea logArea;
    
    private String selectedLog;

    public AdminStartScreen(UserManager userManager, RouteManager routeManager){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminStartScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.routeManager = routeManager;
            this.userManager = userManager;
        } catch (IOException e) {
            e.printStackTrace();
        }
        logCbx.setEditable(false);
        logArea.setEditable(false);
    }

    public void startDay(javafx.event.ActionEvent actionEvent) {
        if(!Logger.isActive()) {
            Logger.startDay();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("System On");
            alert.setContentText("Warning! The system is already active");
            alert.showAndWait();
        }
    }

    
    public void endDay(javafx.event.ActionEvent actionEvent) {
        if(Logger.isActive()) {
            Logger.endDay();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("System Off");
            alert.setContentText("Warning! The system is already not active");
            alert.showAndWait();
        }
    }
    
    public void generateLog() {
      Logger.generateLog();
    }
    
    public void refreshLogs() {
      File logFolder = new File("logs/");
      File[] contents = logFolder.listFiles();
      
      ObservableList<String> fileNames = FXCollections.observableArrayList();
      
      for (File f : contents) {
        fileNames.add(f.getName());
      }
      
      logCbx.setItems(fileNames);
    }
    
    public void readLog() {
      File selectedFile = new File("logs/"+logCbx.getValue());
      StringBuilder output = new StringBuilder();
      try {
        Scanner scanner = new Scanner(selectedFile);
        while (scanner.hasNextLine()) {
          output.append(scanner.nextLine() + "\n");
        }
        scanner.close();
      } catch (Exception e) {
        System.out.println("Error reading logfile.");
      }
      logArea.setText(output.toString());
    }
    
}
