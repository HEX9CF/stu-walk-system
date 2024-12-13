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
 * This class is responsible for reading data from an Excel sheet and converting it to a list of Didian objects.
 */
public class Floor2ReadSheet1 {

    private static final String EXCEL_FILE_PATH = "D:\\IdeaProjects\\HTML\\redMove\\floor2.xlsx"; // Excel file path

    /**
     * Reads the Excel file and returns a list of Didian objects.
     * 
     * @return a list of Didian objects
     */
    public List<Didian> read() {
        return readExcelToArray(EXCEL_FILE_PATH, "Sheet1");
    }

    /**
     * Reads the specified Excel sheet and converts it into a list of Didian objects.
     * 
     * @param filePath the path to the Excel file
     * @param sheetName the name of the sheet to read
     * @return a list of Didian objects
     */
    public static List<Didian> readExcelToArray(String filePath, String sheetName) {
        List<Didian> didianList = new ArrayList<>();
        
        // Try-with-resources to ensure the resources are closed properly
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Didian didian = new Didian();
                    didian.setName(row.getCell(0).toString());
                    didian.setX(row.getCell(1).getNumericCellValue());
                    didian.setY(row.getCell(2).getNumericCellValue());
                    didianList.add(didian);
                }
            }

        } catch (IOException e) {
            // Log the error for better debugging (you can replace with your own logging solution)
            e.printStackTrace();
        }
        
        return didianList;
    }
}
