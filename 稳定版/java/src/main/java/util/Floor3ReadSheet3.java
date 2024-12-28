package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is responsible for reading data from the third sheet of the Excel file (floor3.xlsx).
 * The data is expected to be a matrix, where each cell can either be a numeric value or a string.
 * "Inf" strings are converted to a large number (99999999).
 */
public class Floor3ReadSheet3 {

    /**
     * Reads data from the third sheet of the Excel file and returns it as a 2D array of doubles.
     * 
     * @return A 2D array representing the data from the third sheet.
     */
    public double[][] read() {
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/floor3.xlsx";
        // For local testing, you may use a local path instead:
        // String excelFilePath = "D:\\IdeaProjects\\HTML\\redMove\\floor3.xlsx";

        return readExcelToArray(excelFilePath, "Sheet3");
    }

    /**
     * Reads the specified Excel sheet and converts it into a 2D array of doubles.
     * Each cell is expected to contain either a numeric value or a string ("Inf" is converted to 99999999).
     * 
     * @param filePath The file path of the Excel file.
     * @param sheetName The name of the sheet to read.
     * @return A 2D array containing the data from the sheet.
     */
    public static double[][] readExcelToArray(String filePath, String sheetName) {
        double[][] dataArray = null;

        // Open the Excel file and read the data
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();  // Get number of rows
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();  // Get number of columns

            // Initialize the 2D array to hold the data
            dataArray = new double[rowCount][colCount];

            // Iterate through each row
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;  // Skip empty rows

                // Iterate through each cell in the row
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);

                    if (cell == null) continue;  // Skip empty cells

                    // Process the cell based on its type
                    switch (cell.getCellType()) {
                        case STRING:
                            String cellValue = cell.getStringCellValue();
                            if ("Inf".equals(cellValue)) {
                                // Convert "Inf" to a large number
                                dataArray[i][j] = 99999999;
                            } else {
                                // Log an error for unexpected string values
                                System.err.println("Unexpected string value: " + cellValue);
                            }
                            break;
                        case NUMERIC:
                            dataArray[i][j] = cell.getNumericCellValue();
                            break;
                        default:
                            // Log an error for unexpected cell types (like formulas)
                            System.err.println("Unexpected cell type: " + cell.getCellType());
                            break;
                    }
                }
            }
        } catch (IOException e) {
            // Log the error if the file can't be read
            e.printStackTrace();
        }

        return dataArray;
    }
}
