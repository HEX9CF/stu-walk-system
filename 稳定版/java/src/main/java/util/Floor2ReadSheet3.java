package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is responsible for reading data from the "floor2.xlsx" Excel file, specifically from the sheet named "Sheet3".
 * It extracts the numeric data and handles special cases such as the string "Inf".
 */
public class Floor2ReadSheet3 {

    /**
     * Reads data from the "floor2.xlsx" Excel file and returns the data from "Sheet3" as a 2D array.
     *
     * @return A 2D array containing the numeric data from the Excel sheet.
     */
    public double[][] read() {
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/floor2.xlsx";
        // For local testing, you can use the following path instead:
        // String excelFilePath = "D:\\IdeaProjects\\HTML\\redMove\\floor2.xlsx";
        
        return readExcelToArray(excelFilePath, "Sheet3");
    }

    /**
     * Reads an Excel file and extracts numeric data from a specific sheet, returning it as a 2D array.
     * Special handling for the string "Inf", which is treated as a large numeric value (99999999).
     *
     * @param filePath  The file path of the Excel file.
     * @param sheetName The name of the sheet to read data from.
     * @return A 2D array containing the extracted numeric data.
     */
    public static double[][] readExcelToArray(String filePath, String sheetName) {
        double[][] dataArray = null;
        
        // Try-with-resources to ensure the file stream and workbook are properly closed
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.err.println("Sheet '" + sheetName + "' does not exist in the file.");
                return dataArray;
            }

            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
            dataArray = new double[rowCount][colCount];

            // Process each row and cell in the sheet
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // Skip empty rows

                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) continue; // Skip empty cells

                    // Handle different cell types
                    switch (cell.getCellType()) {
                        case STRING:
                            String cellValue = cell.getStringCellValue();
                            if ("Inf".equals(cellValue)) {
                                dataArray[i][j] = 99999999;  // Handle "Inf" as a large numeric value
                            } else {
                                System.err.println("Unexpected string value: " + cellValue);
                            }
                            break;
                        case NUMERIC:
                            dataArray[i][j] = cell.getNumericCellValue();  // Handle numeric values
                            break;
                        default:
                            // Handle other unexpected cell types (e.g., formulas)
                            System.err.println("Unexpected cell type: " + cell.getCellType());
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the Excel file: " + e.getMessage());
            e.printStackTrace();
        }

        return dataArray;
    }
}
