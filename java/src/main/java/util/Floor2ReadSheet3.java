package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is responsible for reading data from the specified Excel sheet and converting it into a 2D double array.
 */
public class Floor2ReadSheet3 {

    private static final String EXCEL_FILE_PATH = "D:\\IdeaProjects\\HTML\\redMove\\floor2.xlsx"; // Excel file path

    /**
     * Reads data from an Excel sheet and returns it as a 2D double array.
     * 
     * @return a 2D double array representing the sheet's data
     */
    public double[][] read() {
        return readExcelToArray(EXCEL_FILE_PATH, "Sheet3");
    }

    /**
     * Reads the specified Excel sheet and converts it into a 2D double array.
     * 
     * @param filePath the path to the Excel file
     * @param sheetName the name of the sheet to read
     * @return a 2D double array representing the sheet's data
     */
    public static double[][] readExcelToArray(String filePath, String sheetName) {
        double[][] dataArray = null;
        
        // Try-with-resources to ensure proper resource management
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            dataArray = new double[rowCount][colCount];

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
                                dataArray[i][j] = 99999999;
                            } else {
                                // Log unexpected string values
                                System.err.println("Unexpected string value: " + cellValue);
                            }
                            break;
                        case NUMERIC:
                            dataArray[i][j] = cell.getNumericCellValue();
                            break;
                        default:
                            // Handle other cell types (e.g., formula cells)
                            System.err.println("Unexpected cell type: " + cell.getCellType());
                            break;
                    }
                }
            }

        } catch (IOException e) {
            // Log the error
            e.printStackTrace();
        }

        return dataArray;
    }
}
