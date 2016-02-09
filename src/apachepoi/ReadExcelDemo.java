package apachepoi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

/**
 * Created by kumars on 2/3/2016.
 */
//import statements
public class ReadExcelDemo
{
    //comment sample
    //
    public static void main(String... args)
    {
        try
        {
            FileInputStream file = new FileInputStream(new File("C:\\Users\\AroraA\\IdeaProjects\\ExcelReader\\ExcelFiles\\test report.xls"));

            //Create Workbook instance holding reference to .xlsx file
            Workbook workbook = new HSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            for (Row row : sheet) {
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                //iterator
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                    }
                }
                System.out.println("");
            }
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
