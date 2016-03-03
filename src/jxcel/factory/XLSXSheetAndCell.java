package jxcel.factory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Saurabh on 3/3/2016.
 */
public class XLSXSheetAndCell {

    FileInputStream inputWorkbook = null;
    Workbook workbook = null;
    Sheet sheet = null;
    Cell cell = null;

    public Sheet ApacheXLSXSheet(String empListID) {

        try {
            inputWorkbook = new FileInputStream(empListID);

            workbook = new XSSFWorkbook(inputWorkbook);
            sheet = workbook.getSheetAt(0);          // Get the first sheet
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sheet;
    }

    public String getCustomCellContent(int column, int row) {
        Cell cell = sheet.getRow(row).getCell(column);
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
            return cell.getStringCellValue();
        else
            return cell.getStringCellValue();
    }
}
