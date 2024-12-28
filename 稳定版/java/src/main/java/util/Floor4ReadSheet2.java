package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * The Floor4ReadSheet2 class is responsible for reading data from an Excel file
 * (specifically from Sheet2) and returning the data as a 2D array of doubles.
 * It processes both numeric values and specific string values (e.g., "Inf").
 */
public class Floor4ReadSheet2 {

    /**
     * Reads data from the "Sheet2" of the specified Excel file and returns the data as a 2D array.
     * The data can include numeric values and string values (such as "Inf", which is treated as a large number).
     *
     * @return A 2D array of doubles containing the data read from the Excel sheet.
     */
    public double[][] read() {
        // Define the path to the Excel file
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/floor4.xlsx";
        // Alternatively, you can use a local path:
        // String excelFilePath = "D:\\IdeaProjects\\HTML\\redMove\\floor4.xlsx";

        // Read the data from the Excel file and return it as a 2D array
        return readExcelToArray(excelFilePath, "Sheet2");
    }

    /**
     * Reads data from the given Excel file and sheet, and returns the data as a 2D array.
     * The data is read cell by cell, and string values like "Inf" are treated as large numbers.
     * Numeric values are stored as they are.
     *
     * @param filePath  The path to the Excel file.
     * @param sheetName The name of the sheet to read from.
     * @return A 2D array of doubles containing the data from the Excel sheet.
     */
    public static double[][] readExcelToArray(String filePath, String sheetName) {
        double[][] dataArray = null;
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Access the specified sheet in the Excel file
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows(); // Get the number of rows in the sheet
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells(); // Get the number of columns in the first row

            // Initialize the data array with dimensions based on the sheet's row and column counts
            dataArray = new double[rowCount][colCount];

            // Loop through each row in the sheet
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i); // Get the current row
                if (row == null) continue; // Skip empty rows

                // Loop through each cell in the row
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j); // Get the current cell
                    if (cell == null) continue; // Skip empty cells

                    // Process the cell based on its type
                    switch (cell.getCellType()) {
                        case STRING:
                            String cellValue = cell.getStringCellValue();
                            if ("Inf".equals(cellValue)) {
                                dataArray[i][j] = 99999999; // Treat "Inf" as a large number
                            } else {
                                // Optionally handle other string values or throw an exception
                                System.err.println("Unexpected string value: " + cellValue);
                            }
                            break;
                        case NUMERIC:
                            dataArray[i][j] = (int) cell.getNumericCellValue(); // Store numeric values
                            break;
                        default:
                            // Handle other cell types (e.g., formulas) as needed
                            System.err.println("Unexpected cell type: " + cell.getCellType());
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print any I/O exceptions that occur
        }
        
        // Return the populated 2D array
        return dataArray;
    }
}
