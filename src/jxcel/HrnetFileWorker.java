package jxcel;

import factory.XLSXSheetAndCell;
import model.HrnetColumns;
import model.HrnetDetails;
import model.IHrnetFile;
import model.attendence.AttendanceOfLeave;
import model.attendence.LeaveType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

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
    Sheet sheet;

    public HrnetFileWorker(String hrNetFile) {
        sheet = new XLSXSheetAndCell().ApacheXLSXSheet(hrNetFile);
    }


    private String getID(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_STRING)
            return cell.getStringCellValue();
        else
            return Objects.toString(cell.getNumericCellValue());
    }


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
        String salesForceID = null;
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
                        hre = HrnetColumns.WORKER_ID;
                        salesForceID = getID(cell);
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
                        if (tempDate.getMonth().equals(JxcelBiometricFileWorker.month))
                            attendanceOfLeave.setStartDate(tempDate);
                        break;
                    case 5:
                        hre = HrnetColumns.END_DATE;
                        tempDate = getLocalDate(cell);
                        if (attendanceOfLeave.getStartDate() != null)
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

            /**
             * only consider the salesforce data for those months which is on biometric excel
             */
            if (attendanceOfLeave.getStartDate() != null) {
                if (hrnetDetails.containsKey(salesForceID)) {
                    tempArrLst = hrnetDetails.get(salesForceID);
                    tempArrLst.add(new HrnetDetails(salesForceID, empName, empRequest, attendanceOfLeave));
                    hrnetDetails.put(salesForceID, tempArrLst);

                } else {
                    tempArrLst = new ArrayList<>();
                    tempArrLst.add(new HrnetDetails(salesForceID, empName, empRequest, attendanceOfLeave));
                    hrnetDetails.put(salesForceID, tempArrLst);
                }
            }
        }

    }

    @Override
    public void displayHRNetFile() {
        Set<Map.Entry<String, ArrayList<HrnetDetails>>> s = hrnetDetails.entrySet();

        for (Map.Entry<String, ArrayList<HrnetDetails>> entry : s) {
            entry.getValue().forEach(HrnetDetails::printHrNetDetail);
        }
    }
}

