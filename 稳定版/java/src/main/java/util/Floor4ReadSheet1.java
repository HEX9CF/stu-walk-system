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
 * The Floor4ReadSheet1 class is responsible for reading data from an Excel file
 * (specifically from Sheet1) that contains point information (name, x, y coordinates),
 * and returning a list of Point objects.
 */
public class Floor4ReadSheet1 {

    /**
     * Reads the point data from the specified Excel file and returns a list of Point objects.
     * It reads data from the "Sheet1" of the Excel file, where each row corresponds to a point.
     *
     * @return A list of Point objects, each representing a point's name and coordinates (x, y).
     */
    public List<Point> read() {
        // Define the path to the Excel file
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/floor4.xlsx";
        // Alternatively, you can use a local path:
        // String excelFilePath = "D:\\IdeaProjects\\HTML\\redMove\\floor4.xlsx";
        
        // Read the data from the Excel file and return the list of points
        return readExcelToArray(excelFilePath, "Sheet1");
    }

    /**
     * Reads the Excel file from the given file path and extracts point data from the specified sheet.
     * It reads the first three columns for each row: name, x-coordinate, and y-coordinate, and 
     * creates Point objects for each row of data.
     *
     * @param filePath The path to the Excel file.
     * @param sheetName The name of the sheet from which data will be read.
     * @return A list of Point objects extracted from the Excel sheet.
     */
    public static List<Point> readExcelToArray(String filePath, String sheetName) {
        List<Point> pointList = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Access the specified sheet in the Excel file
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows(); // Get number of rows in the sheet
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells(); // Get number of columns in the first row

            // Loop through each row in the sheet
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i); // Get the current row
                Point point = new Point();
                
                // Extract data from the first three columns: name, x-coordinate, and y-coordinate
                point.setName(row.getCell(0).toString());
                point.setX(row.getCell(1).getNumericCellValue());
                point.setY(row.getCell(2).getNumericCellValue());
                
                // Add the point to the list
                pointList.add(point);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print any I/O exceptions that occur
        }
        
        // Return the list of points
        return pointList;
    }
}
