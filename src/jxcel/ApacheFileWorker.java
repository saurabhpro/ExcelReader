package jxcel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Created by Saurabh on 2/10/2016.
 */
public class ApacheFileWorker implements IApachePOIFile {
    int numberOfRowsInHr;
    HrnetDetails[] hrnetDetails;

    public void readHRNetFile(String hrNetFile) throws IOException {
        FileInputStream file = new FileInputStream(new File(hrNetFile));
        //Create Workbook instance holding reference to .xlsx file

        Workbook workbook = new XSSFWorkbook(file);

        //Get first/desired sheet from the workbook
        Sheet sheet = workbook.getSheetAt(0);

        Cell cell = null;

        numberOfRowsInHr = sheet.getPhysicalNumberOfRows();
        hrnetDetails = new HrnetDetails[numberOfRowsInHr];

        String[] details = new String[7];
        for (int i = 1; i < numberOfRowsInHr; i++) {
            for (int j = 0; j < 7; j++) {
                //Update the value of cell
                cell = sheet.getRow(i).getCell(j);

                java.util.Date d = null;
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String buy_date = null;

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        if (j == 4 || j == 5) {
                            d = cell.getDateCellValue();
                            buy_date = df.format(d);
                            details[j] = buy_date;
                            continue;
                        }
                        details[j] = Objects.toString(cell.getNumericCellValue());

                        break;
                    case Cell.CELL_TYPE_STRING:
                        details[j] = cell.getStringCellValue();
                        break;
                }
                //  System.out.println(details[j]);

            }
            hrnetDetails[i - 1] = new HrnetDetails(details[1], details[0], details[2], details[3], details[4], details[5], details[6]);
        }


        file.close();
    }

    @Override
    public void displayApachePOIFile(HrnetDetails[] x) {
        for (int i = 0; i < numberOfRowsInHr - 1; i++) {
            System.out.print(hrnetDetails[i].hrID);
            System.out.print("\t" + hrnetDetails[i].name);
            System.out.print("\t" + hrnetDetails[i].requestID);
            System.out.print("\t" + hrnetDetails[i].leaveType);
            System.out.print("\t" + hrnetDetails[i].startDate + "\t" + hrnetDetails[i].endDate);
            System.out.println("\t" + hrnetDetails[i].absenceTime);
        }
    }
}
