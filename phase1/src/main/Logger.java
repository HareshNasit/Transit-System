package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Logger {
  private static String day;
  private static boolean active = false;
  private static ArrayList<String> logs;
  private static ArrayList<String> stats;

  public static void log(String logString) {
    if (!active) throw new RuntimeException("Attempted logging when non-active day");
    logs.add(logString);
  }
  
  public static void logStats(String statString) {
    if (!active) throw new RuntimeException("Attempted logging when non-active day");
    stats.add(statString);
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
      
      for (String line : stats) {
        writer.write(line + "\n");
      }
      
      writer.close();
    } catch (FileNotFoundException e) {
      System.out.println("Failed to write log file: " + e.getMessage());
    }

    active = false;
  }
}
