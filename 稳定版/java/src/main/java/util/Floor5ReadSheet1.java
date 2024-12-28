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
 * The Floor5ReadSheet1 class is responsible for reading data from an Excel file (specifically "floor5.xlsx"),
 * extracting the point information (name, x-coordinate, and y-coordinate) from the specified sheet ("Sheet1"),
 * and storing this information in a list of Point objects.
 */
public class Floor5ReadSheet1 {

    /**
     * This method reads the data from the Excel file and extracts point information from "Sheet1".
     * It returns a list of Point objects containing the extracted data.
     * 
     * @return A list of Point objects containing the name, x-coordinate, and y-coordinate.
     */
    public List<Point> read() {
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/floor5.xlsx";
        // Uncomment the line below if running locally (change the path accordingly)
        // String excelFilePath = "D:\\IdeaProjects\\HTML\\redMove\\floor5.xlsx";

        List<Point> dataArray = readExcelToArray(excelFilePath, "Sheet1");
        return dataArray;
    }

    /**
     * This method reads the Excel file and extracts data from the specified sheet.
     * It parses the data and creates Point objects with the extracted values.
     * 
     * @param filePath The path to the Excel file.
     * @param sheetName The name of the sheet to read the data from.
     * @return A list of Point objects.
     */
    public static List<Point> readExcelToArray(String filePath, String sheetName) {
        List<Point> pointList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            // Iterate through all the rows and extract point data
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                Point point = new Point();
                point.setName(row.getCell(0).toString());  // Extract point name
                point.setX(row.getCell(1).getNumericCellValue());  // Extract x-coordinate
                point.setY(row.getCell(2).getNumericCellValue());  // Extract y-coordinate
                pointList.add(point);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pointList;
    }
}
