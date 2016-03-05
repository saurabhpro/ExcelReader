package factory;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

/**
 * Created by kumars on 3/3/2016.
 */
public class JXLSSheetAndCell {
    private Sheet sheet = null;

    public Sheet JXLSSheet(String biometricFile) {
        try {
            File inputWorkbook = new File(biometricFile);
            Workbook w = Workbook.getWorkbook(inputWorkbook);
            sheet = w.getSheet(0);          // Get the first sheet
        } catch (BiffException | IOException e) {
            e.printStackTrace();
        }
        return sheet;
    }
}
