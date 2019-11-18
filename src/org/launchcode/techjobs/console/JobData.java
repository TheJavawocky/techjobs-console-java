package org.launchcode.techjobs.console;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LaunchCode
 */
class JobData {

    private static final String DATA_FILE = "resources/job_data.csv";
    private static Boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;
    static ArrayList<String> findAll(String field) {

        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        return allJobs;
    }

    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);
            aValue = aValue.toLowerCase();//make lowercase for more accurate comparison
            value = value.toLowerCase();//make lowercase for more accurate comparison

            if (aValue.contains(value)) {
                jobs.add(row);
            }
        }
        return jobs;
    }

    public static ArrayList<HashMap<String, String>> findByValue(String searchTerm) {

        loadData();

        ArrayList<HashMap<String, String>> matchingJobs = new ArrayList<>();

        searchTerm = searchTerm.toLowerCase();

        for (HashMap<String, String> jobInArrayList : allJobs) {

            for (Map.Entry<String, String> jobKeySet: jobInArrayList.entrySet()) {
                String jobValue = jobKeySet.getValue();
                jobValue = jobValue.toLowerCase();

                if (jobValue.contains(searchTerm) && !matchingJobs.contains(jobInArrayList)){
                    matchingJobs.add(jobInArrayList);
                }
            }
        }
        return matchingJobs;
    }

    private static void loadData() {

        if (isDataLoaded) {
            return;
        }

        try {
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            int numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }
            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}