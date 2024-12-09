package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * WholeFloorReadSheet3 class is responsible for reading data from a CSV file 
 * (whole_floor.csv) and converting it into a 2D double array. It supports 
 * handling special string values (e.g., "Inf") and converts them to a numerical 
 * representation for further processing.
 */
public class WholeFloorReadSheet3 {

    /**
     * Reads the whole_floor.csv file and returns its contents as a 2D array of doubles.
     *
     * @return 2D array of doubles representing the data from the CSV file.
     */
    public double[][] read() {
        // Path to the CSV file
        String csvFilePath = "/www/wwwroot/database.ccjy16.top/data/whole_floor.csv";
        // Uncomment the following line to use a local file during development
        // String csvFilePath = "D:\\IdeaProjects\\HTML\\redMove\\whole_floor.csv";
        return readCsvToArray(csvFilePath);
    }

    /**
     * Reads the CSV file from the specified file path and converts the data into a 2D array.
     * Each row in the CSV file corresponds to a row in the resulting array, and each value is 
     * converted into a double.
     *
     * @param filePath Path to the CSV file.
     * @return 2D array of doubles representing the CSV data.
     */
    public static double[][] readCsvToArray(String filePath) {
        List<double[]> dataList = new ArrayList<>(); // List to store each row of data as a double array

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the CSV line by commas
                String[] values = line.split(",");
                double[] row = new double[values.length];
                for (int i = 0; i < values.length; i++) {
                    row[i] = parseValue(values[i]); // Convert each string value to a double
                }
                dataList.add(row); // Add the row to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the List to a 2D array and return it
        return dataList.toArray(new double[0][]);
    }

    /**
     * Parses a string value into a double. If the value is "Inf" (case-insensitive), 
     * it returns a large number (99999999). Non-numeric values are converted to 0.0.
     *
     * @param value The string value to be parsed.
     * @return The parsed double value.
     */
    private static double parseValue(String value) {
        try {
            return Double.parseDouble(value); // Attempt to parse the value as a double
        } catch (NumberFormatException e) {
            if ("Inf".equalsIgnoreCase(value)) {
                return 99999999.0; // Return a large number for "Inf"
            }
            return 0.0; // Return 0.0 for any non-numeric values
        }
    }
}
