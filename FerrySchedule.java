import java.io.*;
import java.util.*;

// have the maps as fields
// construct the object with the fileScanner (populate maps)

 /*
 * Jamison Canonizado
 * 7/10/2024
 * CSE 122
 * TA: Marcus Sanchez, Chloe Mi Cartier
 * Culminating Project 1: File Processing and User Input
 * Finding the fast ferry times for Seattle to Bremerton
 * Ferry Times extracted from 
 * https://www.kitsaptransit.com/service/fast-ferry/bremerton-fast-ferry
 */

public class FerrySchedule {
    private Map<String, Integer> bremertonToSeattleDelays;
    private Map<String, Integer> seattleToBremertonDelays;
    private List<String> fileContent;


    /**
     * Behavior: Constructor that initializes the delay maps and processes the file to populate content.
     * Exceptions: None.
     * Parameters: 
     *  - fileScanner: Scanner object used to read the file containing the ferry schedule.
     * Return: None (Constructor).
     */

    public FerrySchedule(Scanner fileScanner) {
        this.bremertonToSeattleDelays = new HashMap<>();
        this.seattleToBremertonDelays = new HashMap<>();
        this.fileContent = processFile(fileScanner);
    }

    /**
     * Behavior: Reads content from the provided file and stores it in a list.
     * Exceptions: None.
     * Parameters: 
     *  - fileScanner: Scanner object used to read the file containing the ferry schedule.
     * Return: A list of strings representing each line from the file.
     */

    private List<String> processFile(Scanner fileScanner) {
        List<String> content = new ArrayList<>();
        while (fileScanner.hasNextLine()) {
            content.add(fileScanner.nextLine());
        }
        return content;
    }

    // instance methods

    /**
     * Behavior: Prints the content of the ferry schedule file.
     * Exceptions: None.
     * Parameters: None.
     * Return: None.
     */

    public void readFile() {
        for (String line : fileContent) {
            System.out.println(line);
        }
    }

    /*
     * Inputs ferry delays from the user.
     * Console Scanner object for reading user input.
     * bremertonToSeattleDelays List to store delay information for Bremerton to Seattle.
     * seattleToBremertonDelays List to store delay information for Seattle to Bremerton.
     */

    public void inputDelays(Scanner console) {
        System.out.println();
        System.out.println("Enter the ferry (e.g., 12:30 PM): "
        + "(type 'done' when finished inputting delays):");
        boolean inputFinished = false;
        while (!inputFinished) {
            System.out.println("Enter the route (B for Bremerton to Seattle, S for Seattle to Bremerton):");
            String route = console.nextLine().toUpperCase();
            if (route.equals("DONE")) {
                inputFinished = true;
            } else if (route.equals("B")) {
                System.out.println();
                System.out.println("Enter the delayed ferry time from Bremerton to Seattle:");
                String time = console.nextLine();
                System.out.println();
                System.out.println("Enter the delay in minutes for Bremerton to Seattle at " + time + ":");
                int delay = console.nextInt();
                console.nextLine();
                bremertonToSeattleDelays.put(time, delay);
            } else if (route.equals("S")) {
                System.out.println("Enter the delayed ferry time from Seattle to Bremerton:");
                String time = console.nextLine();
                System.out.println();
                System.out.println("Enter the delay in minutes for Seattle to Bremerton at " + time + ":");
                int delay = console.nextInt();
                console.nextLine();
                seattleToBremertonDelays.put(time, delay);
            } else {
                System.out.println("Invalid input. Please enter 'B' or 'S' or 'done' to finish.");
            }
        }
    }

    /*
     * Displays the entered ferry delays.
     * bremertonToSeattleDelays List of delay information for Bremerton to Seattle.
     * seattleToBremertonDelays List of delay information for Seattle to Bremerton.
     */

    public void displayDelays() {
        System.out.println("Current Ferry Delays:");
        System.out.println("Bremerton to Seattle:");
        for (String time : bremertonToSeattleDelays.keySet()) {
            System.out.println(time + ": " + bremertonToSeattleDelays.get(time) + " minutes delay");
        }

        System.out.println("Seattle to Bremerton:");
        for (String time : seattleToBremertonDelays.keySet()) {
            System.out.println(time + ": " + seattleToBremertonDelays.get(time) + " minutes delay");
        }
    }

    /**
     * Behavior: Returns the map of delays for the Bremerton to Seattle route.
     * Exceptions: None.
     * Parameters: None.
     * Return: Map containing delay information for Bremerton to Seattle.
     */

    public Map<String, Integer> getBremertonToSeattleDelays() {
        return bremertonToSeattleDelays;
    }

    /**
     * Behavior: Returns the map of delays for the Seattle to Bremerton route.
     * Exceptions: None.
     * Parameters: None.
     * Return: Map containing delay information for Seattle to Bremerton.
     */

    public Map<String, Integer> getSeattleToBremertonDelays() {
        return seattleToBremertonDelays;
    }

    /**
     * Behavior: Calculates and displays the recommended arrival time based on delays.
     * Exceptions: None.
     * Parameters: 
     *  - console: Scanner object for reading user input.
     * Return: None.
     */

    public void calculateArrivalTime(Scanner console) {
        System.out.println("Would you like to calculate your recommended arrival time based on the current delays? (Y/N)");
        String response = console.nextLine().toUpperCase();

        if (response.equals("Y")) {
            System.out.println("Enter the route (B for Bremerton to Seattle, S for Seattle to Bremerton):");
            String route = console.nextLine().toUpperCase();

            if (route.equals("B") && !getBremertonToSeattleDelays().isEmpty()) {
                System.out.println("Enter the scheduled ferry time (e.g., 12:30 PM):");
                String time = console.nextLine();
                if (getBremertonToSeattleDelays().containsKey(time)) {
                    int delay = getBremertonToSeattleDelays().get(time);
                    String newTime = adjustTime(time, delay);
                    System.out.println("With the delay, the ferry will now depart at " + newTime +
                                    ". You should arrive at the terminal accordingly.");
                } else {
                    System.out.println("No delay information available for this time.");
                }
                
            } else if (route.equals("S") && !getSeattleToBremertonDelays().isEmpty()) {
                System.out.println("Enter the scheduled ferry time (e.g., 12:30 PM):");
                String time = console.nextLine();
                if (getSeattleToBremertonDelays().containsKey(time)) {
                    int delay = getSeattleToBremertonDelays().get(time);
                    String newTime = adjustTime(time, delay);
                    System.out.println("With the delay, the ferry will now depart at " + newTime +
                                    ". You should arrive at the terminal accordingly.");
                } else {
                    System.out.println("No delay information available for this time.");
                }

            } else {
                System.out.println("Invalid input or no delay information available.");
            }
        }
    }

    /**
     * Behavior: Adjusts the time based on the delay and returns the adjusted time.
     * Exceptions: None.
     * Parameters: 
     *  - time: The original scheduled time as a string (e.g., "12:30 PM").
     *  - delay: The delay in minutes to be added to the original time.
     * Return: The adjusted time as a string (e.g., "12:45 PM").
     */

    public String adjustTime(String time, int delay) {
        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1].substring(0, 2));
        String amPm = timeParts[1].substring(3);

        minutes += delay;

        if (minutes >= 60) {
            minutes -= 60;
            hours += 1;
        }

        if (hours > 12) {
            hours -= 12;
            if (amPm.equals("AM")) {
                 amPm = "PM";
        } else {
            amPm = "AM";
        }

        } else {
            if (hours == 12 && minutes == 0) {
        if (amPm.equals("AM")) {
            amPm = "PM";
        } else {
            amPm = "AM";
            }
        }
    }

        String newMinutes;
        if (minutes < 10) {
            newMinutes = "0" + minutes;
        } else {
            newMinutes = "" + minutes;
        }
        return hours + ":" + newMinutes + " " + amPm;
    }
}


