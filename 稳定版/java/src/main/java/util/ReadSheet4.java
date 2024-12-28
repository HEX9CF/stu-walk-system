package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * The ReadSheet4 class is responsible for reading data from an Excel file (specifically "didian.xlsx"),
 * extracting numeric data from the specified sheet ("Sheet4"), and storing this data in a 2D array.
 */
public class ReadSheet4 {

    /**
     * This method reads the data from the Excel file and extracts numeric data from "Sheet4".
     * It returns a 2D array containing the numeric values from the sheet.
     *
     * @return A 2D array representing the numeric data from "Sheet4".
     */
    public double[][] read() {
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/didian.xlsx";
        // Uncomment the line below if running locally (change the path accordingly)
        // String excelFilePath = "D:\\IdeaProjects\\HTML\\redMove\\didian.xlsx";
        
        return readExcelToArray(excelFilePath, "Sheet4");
    }

    /**
     * This method reads the Excel file and extracts numeric data from the specified sheet.
     * It stores the data in a 2D array and returns it.
     *
     * @param filePath The path to the Excel file.
     * @param sheetName The name of the sheet to read the data from.
     * @return A 2D array representing the numeric data from the sheet.
     */
    public static double[][] readExcelToArray(String filePath, String sheetName) {
        double[][] dataArray = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            // Initialize the 2D array to store the numeric data
            dataArray = new double[rowCount][colCount];

            // Iterate through all rows and cells to extract numeric data
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    // Read the numeric value from each cell and store it in the array
                    dataArray[i][j] = cell.getNumericCellValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataArray;
    }
}
