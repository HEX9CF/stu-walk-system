package util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pojo.Point;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for reading data from an Excel file (floor1.xlsx)
 * and extracting points information, including their names and coordinates (X, Y).
 */
public class Floor1ReadSheet1 {

    /**
     * Reads the points data from the specified Excel file and sheet.
     * 
     * @return List of points containing the name and coordinates of each point.
     */
    public List<Point> read() {
        // Define the path to the Excel file containing the points data
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/floor1.xlsx";
        // Call the method to read the data from the specified Excel file and sheet
        List<Point> dataArray = readExcelToArray(excelFilePath, "Sheet1");
        return dataArray; // Return the list of points
    }

    /**
     * Reads the points data from an Excel file and stores the information in a list.
     * 
     * @param filePath Path to the Excel file
     * @param sheetName Name of the sheet to be read
     * @return List of points containing the name and coordinates of each point
     */
    public static List<Point> readExcelToArray(String filePath, String sheetName) {
        // Initialize the list to store points
        List<Point> pointList = new ArrayList<>();
        
        // Try-with-resources to automatically close FileInputStream and Workbook
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Get the specified sheet from the workbook
            Sheet sheet = workbook.getSheet(sheetName);
            // Get the number of rows and columns in the sheet
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            // Loop through the rows of the sheet
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i); // Get the row at index i
                Point point = new Point(); // Create a new Point object

                // Set the name, X, and Y coordinates of the point from the Excel row
                point.setName(row.getCell(0).toString()); // Name is in the first column
                point.setX(row.getCell(1).getNumericCellValue()); // X-coordinate is in the second column
                point.setY(row.getCell(2).getNumericCellValue()); // Y-coordinate is in the third column
                
                // Add the point to the list
                pointList.add(point);
            }
        } catch (IOException e) {
            // Print the exception if there's an error reading the file
            e.printStackTrace();
        }
        
        // Return the list of points
        return pointList;
    }
}
