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
 * This class is responsible for reading data from an Excel file (floor3.xlsx) and converting
 * it into a list of Point objects, each representing a point with a name and coordinates (X, Y).
 */
public class Floor3ReadSheet1 {

    /**
     * Reads data from the specified Excel file and returns a list of Point objects.
     * 
     * @return A list of Point objects containing the data from the Excel file.
     */
    public List<Point> read() {
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/floor3.xlsx";
        // For testing or local development, the path can be adjusted:
        // String excelFilePath = "D:\\IdeaProjects\\HTML\\redMove\\floor3.xlsx";

        return readExcelToArray(excelFilePath, "Sheet1");
    }

    /**
     * Reads an Excel file and converts its data into a list of Point objects.
     * Each row in the Excel sheet is expected to contain a point's name, X coordinate, and Y coordinate.
     * 
     * @param filePath The file path of the Excel file.
     * @param sheetName The name of the sheet to read from.
     * @return A list of Point objects populated with data from the Excel sheet.
     */
    public static List<Point> readExcelToArray(String filePath, String sheetName) {
        List<Point> pointList = new ArrayList<>();
        
        // Open the Excel file and read the data
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();  // Get number of rows
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();  // Get number of columns

            // Iterate through each row and read the data
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;  // Skip empty rows
                
                Point point = new Point();
                
                // Assuming the first cell is the name, second is X, third is Y
                point.setName(row.getCell(0).toString());
                point.setX(row.getCell(1).getNumericCellValue());
                point.setY(row.getCell(2).getNumericCellValue());
                
                pointList.add(point);
            }
        } catch (IOException e) {
            e.printStackTrace();  // Log the error if the file can't be read
        }

        return pointList;
    }
}
