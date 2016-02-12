package jxcel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Saurabh on 2/10/2016.
 */
public class JxcelFileWorker implements IJxcelFile {

    List<EmpDetails> empList = null;
    Iterator<EmpDetails> iterator = null;
    private int ADDROWSTEPS = 0;

    @Override
    public void readBiometricFile(String biometricFile) throws IOException, ParseException {
        File inputWorkbook = new File(biometricFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet

            Sheet sheet = w.getSheet(0);

            empList = new ArrayList<>();
            Cell cell;
            String[] details = new String[2];

            String empTime;

            cell = sheet.getCell(13, 7);
            String monthYear = cell.getContents();
            StringTokenizer st = new StringTokenizer(monthYear, "   ");
            String tempString = null;
            String tempDate = null;
            AttendanceDate[] attendanceDate = null;
            Month month = Month.valueOf(st.nextElement().toString().toUpperCase());
            Year year = Year.parse((String) st.nextElement());

            BiometricAttendanceStatus attendanceStatus = null;

            for (int i = 0; i < 91; i++) {
                details[0] = details[1] = null;

                cell = sheet.getCell(3, 13 + (18 * ADDROWSTEPS));
                details[0] = cell.getContents();

                cell = sheet.getCell(3, 15 + (18 * ADDROWSTEPS));
                details[1] = cell.getContents();

                attendanceDate = new AttendanceDate[31];

                attendanceDate[0] = new AttendanceDate();

                for (int k = 0; k < 31; k++) {
                    attendanceStatus = null;
                    attendanceDate[k] = new AttendanceDate();

                     tempDate = ""+(k+1) + "/" + month.getValue()+ "/" + year;

                    attendanceDate[k].setCurrentDate(tempDate);

                    cell = sheet.getCell(k, 20 + (18 * ADDROWSTEPS));
                    empTime = cell.getContents();

                    st = new StringTokenizer(empTime, "   ");


                   // lb:
                    for (int j = 2; j < 6; j++) {
                        if (st.hasMoreElements()) {
                            tempString = (String) st.nextElement();
                            //A
                            //11:00 12:00 00;00 P
                            switch (tempString) {
                                case "A":
                                    attendanceStatus = BiometricAttendanceStatus.ABSENT;
                                    break ;
                                case "P/A":
                                    attendanceStatus = BiometricAttendanceStatus.ABSENT;
                                    break ;
                                case "W":
                                    attendanceStatus = BiometricAttendanceStatus.WEEKEND_HOLIDAY;
                                    break;
                                case "P":
                                    attendanceStatus = BiometricAttendanceStatus.PRESENT;
                                    break ;
                                default:
                                    if (j == 2)
                                        attendanceDate[k].setCheckIn(tempString);
                                    else if (j == 3)
                                        attendanceDate[k].setCheckOut(tempString);
                            }
                        }
                    }
                    attendanceDate[k].setBiometricAttendanceStatus(attendanceStatus);
                }

                empList.add(new EmpDetails(details[0], details[1], attendanceDate));


                ADDROWSTEPS++;
            }
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayBiometricFile() {

        iterator = empList.iterator();

        while (iterator.hasNext()) {
            EmpDetails emp = iterator.next();
            System.out.println("Name: " + emp.name);
            System.out.println("Employee ID: " + emp.empId);

            for (int j = 0; j < 31; j++) {
                System.out.print(emp.attendanceDate[j].getCurrentDate() + " In Time: " + emp.attendanceDate[j].getCheckIn());
                System.out.print("\tOut Time: " + emp.attendanceDate[j].getCheckOut());
                System.out.print("\tStatus: " + emp.attendanceDate[j].getBiometricAttendanceStatus() + "\n");

                TimeManager.calculateTimeDifference(emp.attendanceDate[j].getCheckIn(), emp.attendanceDate[j].getCheckOut(), j + 1);
            }
            System.out.println();
        }
    }
}
