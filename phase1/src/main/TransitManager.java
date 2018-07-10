package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import transitNetwork.RouteManager;
import user.UserManager;

/* This class will perform initial setup upon launch. */
public class TransitManager {
	
	private static final UserManager userManager = new UserManager();
	private static final RouteManager routeManager = new RouteManager();

  public static void main(String[] args) {

      try {

          // Stops file processing
          BufferedReader br = new BufferedReader(new FileReader("config/stops.txt"));
          String fileRead = br.readLine();

          // stopId name
          while (fileRead != null) {
              String[] tokenize = fileRead.split(" ");
              fileRead = br.readLine();
          }

          br.close();

          // Stations file processing
          br = new BufferedReader(new FileReader("config/stations.txt"));
          fileRead = br.readLine();

          // stationId name
          // stationId name connect stopId
          while (fileRead != null) {
              String[] tokenize = fileRead.split(" ");

              fileRead = br.readLine();
          }

          br.close();

          // Routes file processing
          br = new BufferedReader(new FileReader("config/routes.txt"));
          fileRead = br.readLine();

          while (fileRead != null) {
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
