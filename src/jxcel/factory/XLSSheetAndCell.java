package jxcel.factory;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Saurabh on 3/3/2016.
 */
public class XLSSheetAndCell {

    FileInputStream inputWorkbook = null;
    Workbook workbook = null;
    Sheet sheet = null;
    Cell cell = null;

    public Sheet ApacheXLSSheet(String fileName) {
        try {
            FileInputStream file = new FileInputStream(new File(fileName));
            //Create Workbook instance holding reference to .xlsx file
            workbook = new HSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            sheet = workbook.getSheetAt(0);
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
