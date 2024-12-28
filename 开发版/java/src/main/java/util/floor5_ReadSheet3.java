
package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class Floor5ReadSheet3Modified {

    private static final String EXCEL_FILE_PATH = "D:\IdeaProjects\HTML\redMove\floor5.xlsx";

    public double[][] readData() {
        return readExcelToArray(EXCEL_FILE_PATH, "Sheet3");
    }

    private static double[][] readExcelToArray(String filePath, String sheetName) {
        double[][] dataArray = null;
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
                            dataArray[i][j] = "Inf".equals(cellValue) ? Double.POSITIVE_INFINITY : Double.NaN;
                            break;
                        case NUMERIC:
                            dataArray[i][j] = cell.getNumericCellValue();
                            break;
                        default:
                            // Handle other cell types (e.g., formula)
                            System.err.println("Unexpected cell type: " + cell.getCellType());
                            break;
                    }
                }
            }
        } catch (IOException e) {
            // Consider using a logging framework instead of printStackTrace
            e.printStackTrace();
        }
        return dataArray;
    }
}