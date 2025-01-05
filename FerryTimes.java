import java.io.*;
import java.util.*;

// javac *.java
// java FerryTimes

 /*
 * Jamison Canonizado
 * 7/10/2024
 * CSE 122
 * Culminating Project 1: File Processing and User Input
 * Finding the fast ferry times for Seattle to Bremerton
 * Ferry Times extracted from 
 * https://www.kitsaptransit.com/service/fast-ferry/bremerton-fast-ferry
 */
 
public class FerryTimes {
    
    /**
    * Main method to run the Ferry Times program
    * Reads the ferry schedule from the provided file and allows the user to
    * see the schedule from Seattle to Bremerton and Bremerton to Seattle
    * Throws an FileNotFoundException if Schedule file isn't found
    * Throws an IllegalArguementException if inputted a wrong command
    */

    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        Scanner fileScanner = new Scanner(new File("schedule.txt"));
        FerrySchedule ferrySchedule = new FerrySchedule(fileScanner);
        String input = "";

        intro();

        while (!input.equals("Q")) {
            menu();
            input = console.nextLine().toUpperCase();

            if (input.equals("I")) {
                ferrySchedule.inputDelays(console);
            } else if (input.equals("D")) {
                ferrySchedule.displayDelays();
            } else if (input.equals("E")) {
                ferrySchedule.calculateArrivalTime(console);
            } else if (input.equals("Q")) {
            } else if (input.equals("")) {
                ferrySchedule.readFile();
            } else {
                throw new IllegalArgumentException("Invalid menu option: " + input);
            }
        }
    }

    /**
     * Prints the introductory message for the Ferry Times program.
     */

    public static void intro() {
        System.out.println("Welcome to the Fast Ferry Times schedule");
        System.out.println("This program will let you find the Fast Ferry times!");
        System.out.println();
    }

    /**
     * Prints the menu.
     */

    public static void menu() {
        System.out.println();
        System.out.println("To find the times, press Enter.");
        System.out.println("To input delays, press I.");
        System.out.println("To see delays, press D.");
        System.out.println("To see estimated arrival times, press E");
        System.out.println("To exit, press Q.");
    }
}

