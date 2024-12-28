package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Utility class for reading data from "Sheet5" of an Excel file and converting it to a 2D array.
 */
public class ReadSheet5 {

    private static final double INF_VALUE = 99999999; // Represents an infinite value

    /**
     * Reads data from the default Excel file's "Sheet5".
     *
     * @return a 2D array of doubles containing the sheet data.
     */
    public double[][] readSheetData() {
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/didian.xlsx";
        return convertSheetToArray(excelFilePath, "Sheet5");
    }

    /**
     * Reads data from the specified Excel sheet and converts it to a 2D array.
     *
     * @param filePath  the path to the Excel file.
     * @param sheetName the name of the sheet to read.
     * @return a 2D array of doubles containing the sheet data.
     */
    public static double[][] convertSheetToArray(String filePath, String sheetName) {
        double[][] sheetData = null;

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Retrieve the specified sheet
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.err.println("Sheet '" + sheetName + "' not found in file: " + filePath);
                return new double[0][0];
            }

            // Get total rows and columns
            int totalRows = sheet.getPhysicalNumberOfRows();
            int totalColumns = sheet.getRow(0).getPhysicalNumberOfCells();

            sheetData = new double[totalRows][totalColumns];

            // Iterate through rows and cells
            for (int i = 0; i < totalRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // Skip empty rows

                for (int j = 0; j < totalColumns; j++) {
                    Cell cell = row.getCell(j);

                    if (cell == null) {
                        sheetData[i][j] = 0.0; // Default value for empty cells
                        continue;
                    }

                    // Process cell value based on its type
                    switch (cell.getCellType()) {
                        case STRING:
                            String cellValue = cell.getStringCellValue();
                            if ("Inf".equalsIgnoreCase(cellValue)) {
                                sheetData[i][j] = INF_VALUE; // Handle "Inf" as a large value
                            } else {
                                System.err.println("Unexpected string value at row " + i + ", column " + j + ": " + cellValue);
                                sheetData[i][j] = 0.0; // Default value for unexpected strings
                            }
                            break;
                        case NUMERIC:
                            sheetData[i][j] = cell.getNumericCellValue();
                            break;
                        default:
                            System.err.println("Unhandled cell type at row " + i + ", column " + j + ": " + cell.getCellType());
                            sheetData[i][j] = 0.0; // Default value for unsupported types
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + filePath);
            e.printStackTrace();
        }

        return sheetData;
    }
}
