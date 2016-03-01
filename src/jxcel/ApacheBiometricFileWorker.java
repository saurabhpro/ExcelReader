package jxcel;

import jxcel.attendence.AttendanceOfDate;
import jxcel.model.AttendanceStatusType;
import jxcel.model.EmpBiometricDetails;
import jxcel.model.IBiometricFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Created by Saurabh on 2/10/2016.
 * updated on 2/13/2016
 * this files is not fully upgraded as of 29/02/2016
 */

public class ApacheBiometricFileWorker implements IBiometricFile {

    static public Map<String, EmpBiometricDetails> empList = null;
    static public Month month;
    static public Year year;

    int numberOfRowsInBio;
    FileInputStream inputWorkbook = null;
    Workbook workbook = null;
    Sheet sheet = null;
    Cell cell = null;
    private int ADD_ROW_STEPS = 0;

    public ApacheBiometricFileWorker(String biometricFile) {
        try {
            inputWorkbook = new FileInputStream(biometricFile);

            workbook = new XSSFWorkbook(inputWorkbook);
            sheet = workbook.getSheetAt(0);          // Get the first sheet
            numberOfRowsInBio = (sheet.getPhysicalNumberOfRows() - 11) / 18;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to return the contents of a given row, col
     */
    private String getCustomCellContent(int column, int row) {
        Cell cell = sheet.getRow(row).getCell(column);
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
            return cell.getStringCellValue();
        else
            return cell.getStringCellValue();
    }

    /**
     * method to return the attendance for an employee for that month
     *
     * @param attendanceOfDate
     */
    private void getMonthlyAttendanceOfEmployee(AttendanceOfDate[] attendanceOfDate) {
        StringTokenizer st;
        // AttendanceOfDate[] attendanceOfDate;
        AttendanceStatusType attendanceStatus;

        int noOfDaysInThatMonth = month.maxLength();

        for (int k = 0; k < noOfDaysInThatMonth; k++) {
            LocalDate tempDate = LocalDate.of(year.getValue(), month, (k + 1));
            attendanceOfDate[k] = new AttendanceOfDate();
            attendanceOfDate[k].setCurrentDate(tempDate);
            attendanceStatus = AttendanceStatusType.NOT_AN_EMPLOYEE;      //default status for an employee

            st = new StringTokenizer(getCustomCellContent(k, 20 + (18 * ADD_ROW_STEPS)), "   ");

            lb:
            for (int j = 2; j < 6; j++) {
                String tempString;
                if (st.hasMoreElements()) {
                    tempString = (String) st.nextElement();
                    //A
                    //11:00 12:00 00;00 P
                    switch (tempString) {
                        case "A":
                        case "A/A":
                        case "P/A":
                            attendanceStatus = AttendanceStatusType.ABSENT;
                            break lb;

                        case "W":
                            //case when employee checks in on weekend or public holiday
                            attendanceStatus = AttendanceStatusType.WEEKEND_HOLIDAY;
                            break lb;

                        case "A/P":
                        case "P":
                            attendanceStatus = AttendanceStatusType.PRESENT;
                            break lb;

                        default:
                            if (j == 2)
                                attendanceOfDate[k].setCheckIn(LocalTime.parse(tempString));
                            else if (j == 3)
                                attendanceOfDate[k].setCheckOut(LocalTime.parse(tempString));
                    }
                }
            }
            attendanceOfDate[k].setAttendanceStatusType(attendanceStatus);
        }
    }


    @Override
    public void readBiometricFile() throws IOException, ParseException {
        //local data
        String empId, empName;
        AttendanceOfDate[] attendanceOfDate;
        empList = new TreeMap<>();

        String monthYear = getCustomCellContent(13, 7);
        String[] st = monthYear.split("   ");

        month = Month.valueOf(st[0].toUpperCase());
        year = Year.parse(st[1]);


        for (int i = 0; i < numberOfRowsInBio; i++) {
            attendanceOfDate = new AttendanceOfDate[31];
            getMonthlyAttendanceOfEmployee(attendanceOfDate);       //referenced

            empName = getCustomCellContent(3, 13 + (18 * ADD_ROW_STEPS));
            empId = getCustomCellContent(3, 15 + (18 * ADD_ROW_STEPS));


            empList.put(empId, new EmpBiometricDetails(empId, empName, attendanceOfDate));

            ADD_ROW_STEPS++;
        }
    }

    @Override
    public void displayBiometricFile() {
        System.out.println(month);
        empList.values().forEach(EmpBiometricDetails::printEmpBiometricDetails);
    }
}