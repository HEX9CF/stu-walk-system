package util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pojo.Didian;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading location data from an Excel sheet.
 */
public class ReadSheet1 {

    /**
     * Reads location data from the default Excel file and sheet.
     *
     * @return a list of {@link Didian} objects containing location data.
     */
    public List<Didian> readLocations() {
        String excelFilePath = "/www/wwwroot/database.ccjy16.top/data/didian.xlsx";
        return readExcelSheetToList(excelFilePath, "Sheet1");
    }

    /**
     * Reads data from the specified Excel sheet into a list of {@link Didian} objects.
     *
     * @param filePath  the path to the Excel file.
     * @param sheetName the name of the sheet to read.
     * @return a list of {@link Didian} objects containing location data.
     */
    public static List<Didian> readExcelSheetToList(String filePath, String sheetName) {
        List<Didian> locationList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Get the specified sheet
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.err.println("Sheet '" + sheetName + "' not found in file: " + filePath);
                return locationList;
            }

            // Iterate through rows
            int rowCount = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // Skip empty rows

                // Create a Didian object and populate its fields
                Didian didian = new Didian();
                didian.setName(row.getCell(0).toString());
                if (row.getCell(1) != null) {
                    didian.setX(row.getCell(1).getNumericCellValue());
                }
                if (row.getCell(2) != null) {
                    didian.setY(row.getCell(2).getNumericCellValue());
                }
                locationList.add(didian);
            }
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + filePath);
            e.printStackTrace();
        }

        return locationList;
    }
}
