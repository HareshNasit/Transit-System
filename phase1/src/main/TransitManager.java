package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import user.Admin;

/* This class will perform initial setup upon launch. */
public class TransitManager {
	
	private final Admin admin = new Admin();

  public static void main(String[] args) {

      try {

          // Stops file processing
          BufferedReader br = new BufferedReader(new FileReader("stops.txt"));
          String fileRead = br.readLine();

          // stopId name
          while (fileRead != null) {
              String[] tokenize = fileRead.split(" ");
          }

          br.close();

          // Stations file processing
          br = new BufferedReader(new FileReader("stations.txt"));
          fileRead = br.readLine();

          // stationId name
          // stationId name connect stopId
          while (fileRead != null) {
              String[] tokenize = fileRead.split(" ");
          }

          br.close();

          // Events file processing
          br = new BufferedReader(new FileReader("events.txt"));
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
          }

      } catch (Throwable e) {
          e.printStackTrace();
          System.exit(1);
      }

  }

}
