package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Logger {
  private static String day;
  private static boolean active = false;
  private static ArrayList<String> logs;
  private static ArrayList<String> stats;
  private static int stopsVisited = 0;

  public static void log(String logString) {
    if (!active) throw new RuntimeException("Attempted logging when non-active day");
    logs.add(logString);
  }
  
  public static void logStats(String statString) {
    if (!active) throw new RuntimeException("Attempted logging when non-active day");
    stats.add(statString);
  }
  
  public static void logStops(int numStops) {
    stopsVisited += numStops;
  }

  static void startDay(String day) {
    if (active) throw new RuntimeException("New day started before previous day ended.");
    Logger.day = day;
    active = true;
    logs = new ArrayList<>();
    stats = new ArrayList<>();
  }
  
  static void endDay() {
    if (!active) throw new RuntimeException("Day ended before day started.");
    try {
      PrintWriter writer = new PrintWriter("logs/Day_" + day + ".txt");
      writer.write("Day: " + day + "\n");
      writer.write("==================\n");
      
      for (String line : logs) {
        writer.write(line + "\n");
      }
      
      writer.write("==================\n\n\n");
      writer.write("Day " + day + "'s aggregated statistics:\n");
      writer.write("Stops visited: " + stopsVisited);
      for (String line : stats) {
        writer.write(line + "\n");
      }
      
      writer.close();
      logs.clear();
      stats.clear();
      stopsVisited = 0;
    } catch (FileNotFoundException e) {
      System.out.println("Failed to write log file: " + e.getMessage());
    }

    active = false;
  }
}
