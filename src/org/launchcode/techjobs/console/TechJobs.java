package org.launchcode.techjobs.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");


                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else {
                String searchField = getUserSelection("Search by:", columnChoices);

                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();


                if (searchField.equals("all")) {
                    ArrayList<HashMap<String, String>> aMatch  = JobData.findByValue(searchTerm);

                    printJobs(aMatch);

                } else {

                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {
        try {
            int choiceIdx;
            boolean validChoice = false;
            String[] choiceKeys = new String[choices.size()];

            int i = 0;
            for (String choiceKey : choices.keySet()) {
                choiceKeys[i] = choiceKey;
                i++;
            }

            do {

                System.out.println("\n" + menuHeader);

                for (int j = 0; j < choiceKeys.length; j++) {
                    System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
                }

                choiceIdx = in.nextInt();
                in.nextLine();

                if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                    System.out.println("Invalid choice. Try again.");
                } else {
                    validChoice = true;
                }

            } while (!validChoice);

            return choiceKeys[choiceIdx];

        } catch (Exception e) {
            System.out.println("Something went wrong.");
            System.exit(0);
        }
        return menuHeader;
    }

    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
        if(!someJobs.isEmpty()){
            for (HashMap<String, String> jobs : someJobs){
                System.out.println("******");
                for (Map.Entry<String, String> job : jobs.entrySet()) {
                    System.out.println(job.getKey() + ": " + job.getValue());
                }
                System.out.println("******\n");
            }

        } else{
            System.out.println("No results.");
        }
    }



}