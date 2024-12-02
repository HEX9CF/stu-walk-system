package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is responsible for reading the data from "Sheet4" of the specified Excel file.
 * It reads the data into a 2D array of doubles, where each cell corresponds to a numeric value.
 */
public class Floor1ReadSheet4 {

    /**
     * Reads data from "Sheet4" of the specified Excel file and stores it in a 2D array.
     * 
     * @return A 2D array containing the numeric values from the sheet.
     */
    public double[][] read() {
        // Define the path to the Excel file containing the data
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/floor1.xlsx";
        // Call the method to read the data from the specified Excel file and sheet
        double[][] dataArray = readExcelToArray(excelFilePath, "Sheet4");
        return dataArray; // Return the 2D array containing the data
    }

    /**
     * Reads data from the specified Excel sheet and stores it in a 2D array.
     * This method handles string values like "Inf" and converts them to a large number (99999999).
     * It also handles numeric values and stores them in the 2D array.
     * 
     * @param filePath Path to the Excel file
     * @param sheetName Name of the sheet to be read
     * @return A 2D array of double values corresponding to the sheet's data
     */
    public static double[][] readExcelToArray(String filePath, String sheetName) {
        double[][] dataArray = null;
        
        // Try-with-resources to automatically close FileInputStream and Workbook
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Get the specified sheet from the workbook
            Sheet sheet = workbook.getSheet(sheetName);
            // Get the number of rows and columns in the sheet
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            // Initialize the 2D array to hold the data
            dataArray = new double[rowCount][colCount];

            // Loop through the rows of the sheet
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i); // Get the row at index i
                if (row == null) continue; // Skip empty rows

                // Loop through the columns of the current row
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j); // Get the cell at index j

                    if (cell == null) continue; // Skip empty cells

                    // Handle different types of cells (String, Numeric, etc.)
                    switch (cell.getCellType()) {
                        case STRING:
                            String cellValue = cell.getStringCellValue();
                            // Convert "Inf" to a large number (99999999)
                            if ("Inf".equals(cellValue)) {
                                dataArray[i][j] = 99999999;
                            } else {
                                // Handle other string values or throw an exception if necessary
                                System.err.println("Unexpected string value: " + cellValue);
                            }
                            break;
                        case NUMERIC:
                            // Store the numeric value as an integer (casting to int)
                            dataArray[i][j] = (int) cell.getNumericCellValue();
                            break;
                        default:
                            // Handle other cell types (e.g., formula cells)
                            System.err.println("Unexpected cell type: " + cell.getCellType());
                            break;
                    }
                }
            }
        } catch (IOException e) {
            // Print the exception if there's an error reading the file
            e.printStackTrace();
        }
        
        // Return the 2D array containing the data from the Excel sheet
        return dataArray;
    }
}
