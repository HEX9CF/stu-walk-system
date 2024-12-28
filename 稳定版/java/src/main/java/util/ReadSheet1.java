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
 * The ReadSheet1 class is responsible for reading data from an Excel file (specifically "didian.xlsx"),
 * extracting point information (name, x, and y coordinates) from the specified sheet ("Sheet1"),
 * and storing this data in a list of Point objects.
 */
public class ReadSheet1 {

    /**
     * This method reads the data from the Excel file and extracts point information from "Sheet1".
     * It returns a list of Point objects containing the name and coordinates (x, y).
     *
     * @return A list of Point objects representing the data from "Sheet1".
     */
    public List<Point> read() {
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/didian.xlsx";
        // Uncomment the line below if running locally (change the path accordingly)
        // String excelFilePath = "D:\\IdeaProjects\\HTML\\redMove\\didian.xlsx";
        
        return readExcelToArray(excelFilePath, "Sheet1");
    }

    /**
     * This method reads the Excel file and extracts point data (name, x, y) from the specified sheet.
     * Each point's data is stored in a Point object, and these objects are added to a list.
     * It returns a list of Point objects.
     *
     * @param filePath The path to the Excel file.
     * @param sheetName The name of the sheet to read the data from.
     * @return A list of Point objects representing the data from the sheet.
     */
    public static List<Point> readExcelToArray(String filePath, String sheetName) {
        List<Point> pointList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            // Iterate through all the rows and cells to extract point data
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // Skip empty rows

                Point point = new Point();
                point.setName(row.getCell(0).toString()); // Set the name of the point
                point.setX(row.getCell(1).getNumericCellValue()); // Set the x coordinate
                point.setY(row.getCell(2).getNumericCellValue()); // Set the y coordinate
                pointList.add(point);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pointList;
    }
}
