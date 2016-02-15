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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Saurabh on 2/10/2016.
 */
public class HrnetFileWorker implements IHrnetFile {
    int numberOfRowsInHr;
    List<HrnetDetails> hrnetDetails = null;
    Iterator<HrnetDetails> iterator = null;

    //hi
    public void readHRNetFile(String hrNetFile) throws IOException {
        FileInputStream file = new FileInputStream(new File(hrNetFile));
        //Create Workbook instance holding reference to .xlsx file

        Workbook workbook = new XSSFWorkbook(file);

        //Get first/desired sheet from the workbook
        Sheet sheet = workbook.getSheetAt(0);
        Cell cell = null;

        AttendanceOfLeave attendanceOfLeave = null;

        String tempName = null;
        String tempID = null;
        String tempRequest = null;
        Date d = null;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate tempDate = null;
        HrnetColumns hre = null;

        numberOfRowsInHr = sheet.getPhysicalNumberOfRows();
        hrnetDetails = new ArrayList<>();
        //  hrnetDetails = new HrnetDetails[numberOfRowsInHr];

        for (int i = 1; i < numberOfRowsInHr; i++) {

            attendanceOfLeave = new AttendanceOfLeave();
            for (int j = 0; j < 7; j++) {
                //Update the value of cell
                cell = sheet.getRow(i).getCell(j);

                d = null;
                tempDate = null;
                hre = null;
                // System.out.print(cell.getColumnIndex()+" ");


                switch (j) {
                    case 0:
                        hre = HrnetColumns.EMP_ID;
                        if (cell.getCellType() == Cell.CELL_TYPE_STRING)
                            tempID = cell.getStringCellValue();
                        else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
                            tempID = Objects.toString(cell.getNumericCellValue());
                        break;
                    case 1:
                        hre = HrnetColumns.NAME;
                        tempName = cell.getStringCellValue();
                        break;
                    case 2:
                        hre = HrnetColumns.REQUEST_ID;
                        tempRequest = cell.getStringCellValue();
                        break;
                    case 3:
                        hre = HrnetColumns.ABSENT_REQUEST_TYPE;
                        String tmp = cell.getStringCellValue().replace(" ", "_").toUpperCase();
                        attendanceOfLeave.setLeaveTypes(LeaveTypes.valueOf(tmp));
                        break;
                    case 4:
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            hre = HrnetColumns.START_DATE;
                            tempDate = TimeManager.convertToLocalDate(new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue()));

                        } else
                            tempDate = TimeManager.convertToLocalDate(cell.getStringCellValue());

                        attendanceOfLeave.setStartDate(tempDate);
                        break;
                    case 5:
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            hre = HrnetColumns.END_DATE;
                            tempDate = TimeManager.convertToLocalDate(new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue()));

                        } else
                            tempDate = TimeManager.convertToLocalDate(cell.getStringCellValue());

                        attendanceOfLeave.setEndDate(tempDate);
                        break;

                    case 6:
                        hre = HrnetColumns.ABSENT_TIME_REQUESTED;
                        attendanceOfLeave.setAbsenceTime(cell.getNumericCellValue());
                        break;

                    case 7:
                        hre = HrnetColumns.ABSENT_REQUEST_BY;
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
            System.out.print("\t" + hr.leaveDetails.getStartDate() + "\t" + hr.leaveDetails.getEndDate());
            System.out.println("\t" + hr.leaveDetails.getAbsenceTime());
        }
    }


}

