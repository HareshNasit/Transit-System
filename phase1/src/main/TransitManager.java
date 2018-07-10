package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import transitNetwork.BusStop;
import transitNetwork.RouteManager;
import user.UserManager;

/* This class will perform initial setup upon launch. */
public class TransitManager {

    private static final IDManager idManager = new IDManager();
	private static final UserManager userManager = new UserManager(idManager);
	private static final RouteManager routeManager = new RouteManager(idManager);

	private static String[] extractArgs(String str) {
        // id content pattern
        Pattern stopSyntax = Pattern.compile("(\\S+)\\s(.+)");
        Matcher tokenize = stopSyntax.matcher(str);

        if (!tokenize.find()) return null;
        else {
            String[] result = {tokenize.group(1), tokenize.group(2)};
            return result;
        }
    }

  public static void main(String[] args) {

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

          // Stations file processing
          br = new BufferedReader(new FileReader("config/stations.txt"));
          fileRead = br.readLine();

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

          // Routes file processing
          br = new BufferedReader(new FileReader("config/routes.txt"));
          fileRead = br.readLine();

          // routeId stopIds...
          while (fileRead != null) {
              String[] tokenize = extractArgs(fileRead);

              // only given one argument
              if (tokenize == null) {
                  routeManager.addRoute(fileRead.trim());
              }

              else {
                  ArrayList<BusStop> stops = new ArrayList<>();
                  for (String stopId : tokenize[1].split(" ")) {
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

          // Users file processing
          br = new BufferedReader(new FileReader("config/users.txt"));
          fileRead = br.readLine();

          while (fileRead != null) {
              fileRead = br.readLine();
          }

          br.close();

          // Cards file processing
          br = new BufferedReader(new FileReader("config/cards.txt"));
          fileRead = br.readLine();

          while (fileRead != null) {
              fileRead = br.readLine();
          }

          br.close();

          // Events file processing
          br = new BufferedReader(new FileReader("config/events.txt"));
          fileRead = br.readLine();

          // startup DATE
          if (!fileRead.startsWith("startup")) throw new RuntimeException("No date provided on startup");
          String date = fileRead.split(" ")[1];
          BufferedWriter bw = new BufferedWriter(new FileWriter(date + ".txt"));

          // tapOn
          // tapOff
          // load
          // suspend
          while (fileRead != null) {
              String[] tokenize = fileRead.split(" ");

              fileRead = br.readLine();
          }

          br.close();

      } catch (Throwable e) {
          e.printStackTrace();
          System.exit(1);
      }

  }

}
