package jxcel;

import jxcel.attendence.AttendanceOfLeave;
import jxcel.model.HrnetColumns;
import jxcel.model.HrnetDetails;
import jxcel.model.IHrnetFile;
import jxcel.model.LeaveType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Created by Saurabh on 2/10/2016.
 */
public class HrnetFileWorker implements IHrnetFile {
    public static Map<String, HrnetDetails> hrnetDetails;
    int numberOfRowsInHr;
    Iterator<HrnetDetails> iterator = null;
    FileInputStream file;
    Workbook workbook;
    Sheet sheet;

    public HrnetFileWorker(String hrNetFile) {
        try {
            file = new FileInputStream(new File(hrNetFile));
            //Create Workbook instance holding reference to .xlsx file
            workbook = new XSSFWorkbook(file);
            //Get first/desired sheet from the workbook
            sheet = workbook.getSheetAt(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private String getID(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_STRING)
            return cell.getStringCellValue();
        else
            return Objects.toString(cell.getNumericCellValue());
    }


    @NotNull
    private LocalDate getLocalDate(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
            return TimeManager.convertToLocalDate(new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue()));
        else
            return TimeManager.convertToLocalDate(cell.getStringCellValue());
    }

    //hi
    public void readHRNetFile() throws IOException {
        Cell cell;

        AttendanceOfLeave attendanceOfLeave;

        String tempName = null;
        String tempID = null;
        String tempRequest = null;

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate tempDate;
        HrnetColumns hre = null;

        numberOfRowsInHr = sheet.getPhysicalNumberOfRows();
        hrnetDetails = new TreeMap<>();
        //  hrnetDetails = new HrnetDetails[numberOfRowsInHr];

        for (int i = 1; i < numberOfRowsInHr; i++) {

            attendanceOfLeave = new AttendanceOfLeave();
            for (int j = 0; j < 7; j++) {
                //Update the value of cell
                cell = sheet.getRow(i).getCell(j);

                tempDate = null;
                hre = null;

                switch (j) {
                    case 0:
                        hre = HrnetColumns.EMP_ID;
                        tempID = getID(cell);
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
                        attendanceOfLeave.setLeaveType(LeaveType.valueOf(tmp));
                        break;
                    case 4:
                        hre = HrnetColumns.START_DATE;
                        tempDate = getLocalDate(cell);
                        attendanceOfLeave.setStartDate(tempDate);
                        break;
                    case 5:
                        hre = HrnetColumns.END_DATE;
                        tempDate = getLocalDate(cell);
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
            hrnetDetails.put(tempID, new HrnetDetails(tempID, tempName, tempRequest, attendanceOfLeave));
        }
        file.close();
    }

    @Override
    public void displayHRNetFile() {
        iterator = hrnetDetails.values().iterator();
        while (iterator.hasNext()) {
            HrnetDetails hr = iterator.next();

            System.out.print(hr.employeeID);
            System.out.print("\t" + hr.name);
            System.out.print("\t" + hr.requestID);
            System.out.print("\t" + hr.attendanceOfLeave.getLeaveType());
            System.out.print("\t" + hr.attendanceOfLeave.getStartDate() + "\t" + hr.attendanceOfLeave.getEndDate());
            System.out.println("\t" + hr.attendanceOfLeave.getAbsenceTime());

        }
    }


}

