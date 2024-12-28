
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

public class Floor5ReadSheet1Modified {

    private static final String EXCEL_FILE_PATH = "D:\IdeaProjects\HTML\redMove\floor5.xlsx";

    public List<Didian> readData() {
        return readExcelToArray(EXCEL_FILE_PATH, "Sheet1");
    }

    private static List<Didian> readExcelToArray(String filePath, String sheetName) {
        List<Didian> didianList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rowCount; i++) { // Assuming the first row is header
                Row row = sheet.getRow(i);
                Didian didian = new Didian();
                didian.setName(row.getCell(0).getStringCellValue());
                didian.setX(row.getCell(1).getNumericCellValue());
                didian.setY(row.getCell(2).getNumericCellValue());
                didianList.add(didian);
            }
        } catch (IOException e) {
            // Consider using a logging framework instead of printStackTrace
            e.printStackTrace();
        }
        return didianList;
    }
}