package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * The Floor5ReadSheet3 class is responsible for reading data from an Excel file (specifically "floor5.xlsx"),
 * extracting numerical data (such as distances or weights) from the specified sheet ("Sheet3"), and
 * storing this data in a 2D array of type double.
 */
public class Floor5ReadSheet3 {

    /**
     * This method reads the data from the Excel file and extracts numerical values from "Sheet3".
     * It returns a 2D array of double values representing the data in the sheet.
     *
     * @return A 2D array of double values representing the data from "Sheet3".
     */
    public double[][] read() {
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/floor5.xlsx";
        // Uncomment the line below if running locally (change the path accordingly)
        // String excelFilePath = "D:\\IdeaProjects\\HTML\\redMove\\floor5.xlsx";
        
        return readExcelToArray(excelFilePath, "Sheet3");
    }

    /**
     * This method reads the Excel file and extracts numerical data from the specified sheet.
     * It handles empty rows and cells and converts the cell values to double type for storage in a 2D array.
     * It also handles specific cases, such as "Inf" values that are converted to a large number (99999999).
     *
     * @param filePath The path to the Excel file.
     * @param sheetName The name of the sheet to read the data from.
     * @return A 2D array of double values representing the data from the sheet.
     */
    public static double[][] readExcelToArray(String filePath, String sheetName) {
        double[][] dataArray = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            dataArray = new double[rowCount][colCount];

            // Iterate through all the rows and cells, processing the data
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // Skip empty rows

                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);

                    if (cell == null) continue; // Skip empty cells

                    switch (cell.getCellType()) {
                        case STRING:
                            String cellValue = cell.getStringCellValue();
                            if ("Inf".equals(cellValue)) {
                                // Handle "Inf" string as a large number (99999999)
                                dataArray[i][j] = 99999999;
                            } else {
                                // Log unexpected string values
                                System.err.println("Unexpected string value: " + cellValue);
                            }
                            break;
                        case NUMERIC:
                            // Store numeric values as doubles
                            dataArray[i][j] = cell.getNumericCellValue();
                            break;
                        default:
                            // Handle other unexpected cell types (e.g., formulas)
                            System.err.println("Unexpected cell type: " + cell.getCellType());
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataArray;
    }
}
