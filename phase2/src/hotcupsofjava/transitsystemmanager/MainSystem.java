package hotcupsofjava.transitsystemmanager;

import hotcupsofjava.transitsystemmanager.managers.IDManager;
import hotcupsofjava.transitsystemmanager.managers.RouteManager;
import hotcupsofjava.transitsystemmanager.managers.UserManager;
import hotcupsofjava.transitsystemmanager.objects.TransitSystemObject;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.BusStop;
import hotcupsofjava.transitsystemmanager.objects.userobjects.User;
import hotcupsofjava.transitsystemmanager.screens.AdminScreen.AdminStartScreen;
import hotcupsofjava.transitsystemmanager.screens.UserScreen.UserLoginScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainSystem extends Application {
    private String instanceName;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        instanceName = "TTC";
        IDManager idManager;
        UserManager userManager;
        RouteManager routeManager;

        // attempt to load existing system
        try {
            String baseFilePath = String.format("instances/%s/", instanceName);

            FileInputStream idFileIn = new FileInputStream(baseFilePath + "IDManager.ser");
            ObjectInputStream idObjIn = new ObjectInputStream(idFileIn);
            idManager = (IDManager) idObjIn.readObject();
            TransitSystemObject.setIdManager(idManager);
            IDManager.setInstance(idManager);
            idObjIn.close();

            FileInputStream routeFileIn = new FileInputStream(baseFilePath + "RouteManager.ser");
            ObjectInputStream routeObjIn = new ObjectInputStream(routeFileIn);
            routeManager = (RouteManager) routeObjIn.readObject();
            RouteManager.setInstance(routeManager);
            routeObjIn.close();

            FileInputStream userFileIn = new FileInputStream(baseFilePath + "UserManager.ser");
            ObjectInputStream userObjIn = new ObjectInputStream(userFileIn);
            userManager = (UserManager) userObjIn.readObject();
            UserManager.setInstance(userManager);
            userObjIn.close();
        } catch (Exception e) {
            e.printStackTrace();
            // New system
            idManager = new IDManager();
            TransitSystemObject.setIdManager(idManager);
            userManager = new UserManager();
            routeManager = new RouteManager();
            readBusStops(routeManager);
            readStations(routeManager);
            readRoutes(routeManager);
            readUsers(userManager);
            readInitialCards(userManager);
        }

        openInitialScreens(userManager, routeManager);
    }

    @Override
    public void stop() {
        try {
            String baseFilePath = String.format("instances/%s/", instanceName);
            // Create directory
            new File(baseFilePath).mkdirs();

            FileOutputStream idFileOut = new FileOutputStream(baseFilePath + "IDManager.ser");
            ObjectOutputStream idObjOut = new ObjectOutputStream(idFileOut);
            idObjOut.writeObject(IDManager.getInstance());
            idObjOut.flush();
            idObjOut.close();

            FileOutputStream routeFileOut = new FileOutputStream(baseFilePath + "RouteManager.ser");
            ObjectOutputStream routeObjOut = new ObjectOutputStream(routeFileOut);
            routeObjOut.writeObject(RouteManager.getInstance());
            routeObjOut.flush();
            routeObjOut.close();

            FileOutputStream userFileOut = new FileOutputStream(baseFilePath + "UserManager.ser");
            ObjectOutputStream userObjOut = new ObjectOutputStream(userFileOut);
            userObjOut.writeObject(UserManager.getInstance());
            userObjOut.flush();
            userObjOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void openInitialScreens(UserManager userManager, RouteManager routeManager){

        Stage loginWindow = new Stage();
        UserLoginScreen loginController = new UserLoginScreen(userManager,routeManager);
        loginWindow.initModality(Modality.WINDOW_MODAL);
        loginWindow.setTitle("Login Screen");
        loginWindow.setScene(new Scene(loginController));
        loginWindow.show();

        Stage adminWindow = new Stage();
        AdminStartScreen adminController = new AdminStartScreen(userManager,routeManager);
        adminWindow.initModality(Modality.WINDOW_MODAL);
        adminWindow.setTitle("Admin Screen");
        adminWindow.setScene(new Scene(adminController));
        adminWindow.show();

    }

    private void readInitialCards(UserManager userManager){
        try{
            // Cards file processing
            BufferedReader br = new BufferedReader(new FileReader("config/cards.txt"));
            String fileRead = br.readLine();

            // userId cardIds...
            while (fileRead != null) {
                String[] tokenize = extractArgs(fileRead);

                if (tokenize != null) {
                    String userId = tokenize[0];
                    if (!userManager.hasUser(userId))
                        throw new RuntimeException("Invalid User id provided when adding cards: " + fileRead);
                    User user = userManager.getUser(userId);

                    for (String cardId : tokenize[1].split("\\s+")) {
                        userManager.addCard(user, cardId);
                    }
                }

                fileRead = br.readLine();
            }

            br.close();

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readStations(RouteManager routeManager){
        try{
            BufferedReader br = new BufferedReader(new FileReader("config/stations.txt"));
            String fileRead = br.readLine();

            // stationId name
            // stationId name connect stopId
            while (fileRead != null) {
                String stationString = fileRead;
                BusStop connection = null;
                if (stationString.contains(" connects ")) {
                    String[] connectionString = stationString.split(" connects ");
                    if (!routeManager.hasStop(connectionString[1])) {
                        throw new RuntimeException("Invalid BusStop id provided for station connection: " + fileRead);
                    }
                    connection = routeManager.getStop(connectionString[1]);
                    stationString = connectionString[0];
                }
                String[] tokenize = extractArgs(stationString);

                if (tokenize == null) throw new RuntimeException("Invalid format for station: " + fileRead);
                else if (connection != null) routeManager.addStation(tokenize[0], tokenize[1], connection);
                else routeManager.addStation(tokenize[0], tokenize[1]);

                fileRead = br.readLine();
            }

            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readBusStops(RouteManager routeManager){
        try {
            // Stops file processing
            BufferedReader br = new BufferedReader(new FileReader("config/stops.txt"));
            String fileRead = br.readLine();
            // stopId name
            while (fileRead != null) {
                String[] tokenize = extractArgs(fileRead);
                // only given one argument
                if (tokenize == null) throw new RuntimeException("Invalid format for stop: " + fileRead);
                else routeManager.addStop(tokenize[0], tokenize[1]);
                fileRead = br.readLine();
            }

            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readUsers(UserManager userManager){
        try{
            // Users file processing
            BufferedReader br = new BufferedReader(new FileReader("config/users.txt"));
            String fileRead = br.readLine();

            // userId email@example.com name
            while (fileRead != null) {
                Pattern userSyntax = Pattern.compile("(\\S+)\\s+(\\S+@\\S+\\.\\S+)\\s+(.+)");
                Matcher tokenize = userSyntax.matcher(fileRead);

                if (!tokenize.find()) {
                    throw new RuntimeException("Invalid user syntax; was the email formatted properly?: " + fileRead);
                }

                userManager.addUser(tokenize.group(1), tokenize.group(3), tokenize.group(2));

                fileRead = br.readLine();
            }

            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private void readRoutes(RouteManager routeManager){
        try{
            // Routes file processing
            BufferedReader br = new BufferedReader(new FileReader("config/routes.txt"));
            String fileRead = br.readLine();

            // routeId stopIds...
            while (fileRead != null) {
                String[] tokenize = extractArgs(fileRead);

                // only given one argument
                if (tokenize == null) {
                    routeManager.addRoute(fileRead.trim());
                } else {
                    ArrayList<BusStop> stops = new ArrayList<>();
                    for (String stopId : tokenize[1].split("\\s+")) {
                        if (!routeManager.hasStop(stopId)) {
                            throw new RuntimeException(String.format("Invalid BusStop id (%s) provided for route creation: %s", stopId, fileRead));
                        }
                        stops.add(routeManager.getStop(stopId));
                    }

                    routeManager.addRoute(tokenize[0], stops);
                }

                fileRead = br.readLine();
            }

            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static String[] extractArgs(String str) {
        // id content pattern
        Pattern itemSyntax = Pattern.compile("(\\S+)\\s+(.+)");
        Matcher tokenize = itemSyntax.matcher(str);

        if (!tokenize.find()) return null;
        else {
            String[] result = {tokenize.group(1), tokenize.group(2)};
            return result;
        }
    }
}
