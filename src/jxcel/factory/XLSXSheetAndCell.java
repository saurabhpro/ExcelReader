package jxcel.factory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Saurabh on 3/3/2016.
 */
public class XLSXSheetAndCell {

    FileInputStream file = null;
    Workbook workbook = null;
    Sheet sheet = null;
    Cell cell = null;

    public Sheet ApacheXLSXSheet(String empListID) {

        try {
            file = new FileInputStream(new File(empListID));
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheetAt(0);          // Get the first sheet
            file.close();
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
