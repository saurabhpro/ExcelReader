package jxcel;

import jxcel.attendence.AttendanceOfLeave;
import jxcel.model.HrnetColumns;
import jxcel.model.HrnetDetails;
import jxcel.model.IHrnetFile;
import jxcel.model.LeaveTypes;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Saurabh on 2/10/2016.
 */
public class HrnetFileWorker implements IHrnetFile {
    int numberOfRowsInHr;
    List<HrnetDetails> hrnetDetails = null;
    Iterator<HrnetDetails> iterator = null;

    public void readHRNetFile(String hrNetFile) throws IOException {
        FileInputStream file = new FileInputStream(new File(hrNetFile));
        //Create Workbook instance holding reference to .xlsx file

        Workbook workbook = new XSSFWorkbook(file);

        //Get first/desired sheet from the workbook
        Sheet sheet = workbook.getSheetAt(0);

        Cell cell = null;

        numberOfRowsInHr = sheet.getPhysicalNumberOfRows();
        hrnetDetails = new ArrayList<>();
        //  hrnetDetails = new HrnetDetails[numberOfRowsInHr];

        AttendanceOfLeave attendanceOfLeave = null;

        String tempName = null;
        String tempID = null;
        String tempRequest = null;
        Date d = null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String tempDate = null;
        HrnetColumns hr = null;

        for (int i = 1; i < numberOfRowsInHr; i++) {

            attendanceOfLeave = new AttendanceOfLeave();
            for (int j = 0; j < 7; j++) {
                //Update the value of cell
                cell = sheet.getRow(i).getCell(j);

                d = null;
                tempDate = null;
                hr = null;
                // System.out.print(cell.getColumnIndex()+" ");


                switch (j) {
                    case 0:
                        hr = HrnetColumns.EMP_ID;
                        // tempID = cell.getStringCellValue();
                        tempID = Objects.toString(cell.getNumericCellValue());
                        break;
                    case 1:
                        hr = HrnetColumns.NAME;
                        tempName = cell.getStringCellValue();
                        break;
                    case 2:
                        hr = HrnetColumns.REQUEST_ID;
                        tempRequest = cell.getStringCellValue();
                        break;
                    case 3:
                        hr = HrnetColumns.ABSENT_REQUEST_TYPE;
                        String tmp = cell.getStringCellValue().replace(" ", "_").toUpperCase();
                        attendanceOfLeave.setLeaveTypes(LeaveTypes.valueOf(tmp));
                        break;
                  /*  case 4:
                        System.out.println(j);
                        hr = HrnetColumns.START_DATE;
                        d =  cell.getDateCellValue();
                        tempDate = df.format(d);

                        attendanceOfLeave.setStartDate(tempDate);
                        break;
                    case 5:
                        hr = HrnetColumns.END_DATE;
                        d = cell.getDateCellValue();
                        tempDate = df.format(d);
                        attendanceOfLeave.setEndDate(tempDate);
                        break;*/
                    case 6:
                        hr = HrnetColumns.ABSENT_TIME_REQUESTED;
                        attendanceOfLeave.setAbsenceTime(cell.getNumericCellValue());
                        break;

                    case 7:
                        hr = HrnetColumns.ABSENT_REQEST_BY;
                        break;
                }
            }
            hrnetDetails.add(new HrnetDetails(tempID, tempName, tempRequest, attendanceOfLeave));
        }
        file.close();
    }

    @Override
    public void displayApachePOIFile() {
        iterator = hrnetDetails.iterator();
        while (iterator.hasNext()) {
            HrnetDetails hr = iterator.next();

            System.out.print(hr.hrID);
            System.out.print("\t" + hr.name);
            System.out.print("\t" + hr.requestID);
            System.out.print("\t" + hr.leaveDetails.getLeaveTypes());
            //System.out.print("\t" + hr.leaveDetails.getStartDate() + "\t" + hr.leaveDetails.getEndDate());
            System.out.println("\t" + hr.leaveDetails.getAbsenceTime());
        }
    }
}
