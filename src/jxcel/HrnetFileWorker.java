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
import java.util.*;

/**
 * Created by Saurabh on 2/10/2016.
 */
public class HrnetFileWorker implements IHrnetFile {

    public static Map<String, ArrayList<HrnetDetails>> hrnetDetails;
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

        String empName = null;
        String empID = null;
        String empRequest = null;
        LocalDate tempDate;
        HrnetColumns hre = null;

        numberOfRowsInHr = sheet.getPhysicalNumberOfRows();
        hrnetDetails = new TreeMap<>();

        for (int i = 1; i < numberOfRowsInHr; i++) {
            ArrayList<HrnetDetails> tempArrLst = null;
            attendanceOfLeave = new AttendanceOfLeave();

            for (int j = 0; j < 7; j++) {
                //Update the value of cell
                cell = sheet.getRow(i).getCell(j);

                tempDate = null;
                hre = null;

                switch (j) {
                    case 0:
                        hre = HrnetColumns.EMP_ID;
                        empID = getID(cell);
                        break;

                    case 1:
                        hre = HrnetColumns.NAME;
                        empName = cell.getStringCellValue();
                        break;
                    case 2:
                        hre = HrnetColumns.REQUEST_ID;
                        empRequest = cell.getStringCellValue();
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

            if (attendanceOfLeave.getStartDate().getMonth() == JxcelBiometricFileWorker.month
                    && attendanceOfLeave.getEndDate().getMonth() == JxcelBiometricFileWorker.month) {
                if (hrnetDetails.containsKey(empID)) {
                    tempArrLst = hrnetDetails.get(empID);
                    tempArrLst.add(new HrnetDetails(empID, empName, empRequest, attendanceOfLeave));
                    hrnetDetails.put(empID, tempArrLst);

                } else {
                    tempArrLst = new ArrayList<>();
                    tempArrLst.add(new HrnetDetails(empID, empName, empRequest, attendanceOfLeave));
                    hrnetDetails.put(empID, tempArrLst);
                }
            }
        }
        file.close();
    }

    @Override
    public void displayHRNetFile() {
        Set<Map.Entry<String, ArrayList<HrnetDetails>>> s = hrnetDetails.entrySet();

        for (Map.Entry<String, ArrayList<HrnetDetails>> entry : s) {
            entry.getValue().forEach(HrnetDetails::printHrNetDetail);
        }
    }
}

