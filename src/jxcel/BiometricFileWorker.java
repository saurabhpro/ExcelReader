package jxcel;

import jxcel.attendence.AttendanceOfDate;
import jxcel.model.BiometricAttendanceStatusTypes;
import jxcel.model.BiometricDetails;
import jxcel.model.IBiometricFile;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Saurabh on 2/10/2016.
 * updated on 2/13/2016
 */
public class BiometricFileWorker implements IBiometricFile {

    static public List<BiometricDetails> empList = null;
    public Year year = Year.parse("2016");
    public Month month;
    private Iterator<BiometricDetails> iterator = null;
    private int ADD_ROW_STEPS = 0;

    @Override
    public void readBiometricFile(String biometricFile) throws IOException, ParseException {
        File inputWorkbook = new File(biometricFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet

            Sheet sheet = w.getSheet(0);

            int numberOfRowsInBio = (sheet.getRows() - 11) / 18;

            empList = new ArrayList<>();
            Cell cell;
            String[] details = new String[2];


            cell = sheet.getCell(13, 7);
            String monthYear = cell.getContents();
            StringTokenizer st = new StringTokenizer(monthYear, "   ");

            LocalDate tempDate;
            String tempString;
            BiometricAttendanceStatusTypes attendanceStatus = null;

            AttendanceOfDate[] attendanceOfDate;

            this.month = Month.valueOf(st.nextElement().toString().toUpperCase());
            this.year = Year.parse((String) st.nextElement());


            for (int i = 0; i < numberOfRowsInBio; i++) {
                details[0] = details[1] = null;

                cell = sheet.getCell(3, 13 + (18 * ADD_ROW_STEPS));
                details[0] = cell.getContents();

                cell = sheet.getCell(3, 15 + (18 * ADD_ROW_STEPS));
                details[1] = cell.getContents();

                attendanceOfDate = new AttendanceOfDate[31];

                for (int k = 0; k < 31; k++) {
                    attendanceOfDate[k] = new AttendanceOfDate();

                    tempDate = LocalDate.of(year.getValue(), month, (k + 1));
                    attendanceOfDate[k].setCurrentDate(tempDate);

                    cell = sheet.getCell(k, 20 + (18 * ADD_ROW_STEPS));
                    st = new StringTokenizer(cell.getContents(), "   ");


                    lb:
                    for (int j = 2; j < 6; j++) {
                        if (st.hasMoreElements()) {
                            tempString = (String) st.nextElement();
                            //A
                            //11:00 12:00 00;00 P
                            switch (tempString) {
                                case "A":
                                    attendanceStatus = BiometricAttendanceStatusTypes.ABSENT;
                                    break lb;
                                case "P/A":
                                    attendanceStatus = BiometricAttendanceStatusTypes.ABSENT;
                                    break lb;
                                case "W":

                                    //case when employee checks in on weekend or public holiday
                                    attendanceStatus = BiometricAttendanceStatusTypes.WEEKEND_HOLIDAY;
                                    break lb;
                                case "P":
                                    attendanceStatus = BiometricAttendanceStatusTypes.PRESENT;
                                    break lb;
                                default:
                                    if (j == 2)
                                        attendanceOfDate[k].setCheckIn(LocalTime.parse(tempString));
                                    else if (j == 3)
                                        attendanceOfDate[k].setCheckOut(LocalTime.parse(tempString));
                            }
                        }
                    }
                    attendanceOfDate[k].setBiometricAttendanceStatusTypes(attendanceStatus);
                }

                empList.add(new BiometricDetails(details[0], details[1], attendanceOfDate));


                ADD_ROW_STEPS++;
            }
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayBiometricFile() {

        iterator = empList.iterator();
        LocalTime workTime;
        while (iterator.hasNext()) {
            BiometricDetails emp = iterator.next();
            System.out.println("Name: " + emp.name);
            System.out.println("Employee ID: " + emp.empId);

            for (int j = 0; j < 31; j++) {
                System.out.print(emp.attendanceOfDate[j].getCurrentDate());
                System.out.print("\tIn Time: " + emp.attendanceOfDate[j].getCheckIn());
                System.out.print("\tOut Time: " + emp.attendanceOfDate[j].getCheckOut());
                System.out.print("\tStatus: " + emp.attendanceOfDate[j].getBiometricAttendanceStatusTypes() + "\n");

                workTime = emp.attendanceOfDate[j].getWorkTimeForDay();
                if (workTime != null)
                    System.out.println(workTime);

            }
            System.out.println();
        }
    }
}
