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
 * This class is responsible for reading data from the "floor2.xlsx" Excel file.
 * It extracts the coordinates and names of points from the specified sheet and stores them in a list.
 */
public class Floor2ReadSheet1 {

    /**
     * Reads data from the "floor2.xlsx" Excel file and returns the list of points from "Sheet1".
     *
     * @return A list of Point objects containing the data from the Excel sheet.
     */
    public List<Point> read() {
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/floor2.xlsx";
        // For testing purposes, you can use the following path instead:
        // String excelFilePath = "D:\\IdeaProjects\\HTML\\redMove\\floor2.xlsx";
        
        return readExcelToArray(excelFilePath, "Sheet1");
    }

    /**
     * Reads an Excel file and extracts data from a specific sheet.
     * The data is then converted into a list of Point objects.
     *
     * @param filePath The file path of the Excel file.
     * @param sheetName The name of the sheet to read data from.
     * @return A list of Point objects containing the data from the specified sheet.
     */
    public static List<Point> readExcelToArray(String filePath, String sheetName) {
        List<Point> pointList = new ArrayList<>();
        
        // Use try-with-resources to automatically close the input stream and workbook
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
             
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.err.println("Sheet " + sheetName + " does not exist in the file.");
                return pointList;
            }

            int rowCount = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                
                // Skip empty rows
                if (row == null) {
                    continue;
                }

                // Assuming the first column is the name, second is X and third is Y coordinates
                String name = row.getCell(0) != null ? row.getCell(0).toString() : "";
                double x = row.getCell(1) != null ? row.getCell(1).getNumericCellValue() : 0.0;
                double y = row.getCell(2) != null ? row.getCell(2).getNumericCellValue() : 0.0;

                // Create a new Point object and add it to the list
                Point point = new Point(name, x, y);
                pointList.add(point);
            }
        } catch (IOException e) {
            System.err.println("Error reading the Excel file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return pointList;
    }
}
