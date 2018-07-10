package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Logger {
  private static ArrayList<String> logs = new ArrayList<String>();
  private static ArrayList<String> stats = new ArrayList<String>();
  
  public static void log(String logString) {
    logs.add(logString);
  }
  
  public static void logStats(String statString) {
    stats.add(statString);
  }
  
  protected static void endDay(int day) {
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
  }
}
