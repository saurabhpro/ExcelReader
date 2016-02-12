package jxcel;

import jxcel.attendence.AttendanceOfDate;
import jxcel.model.BiometricAttendanceStatusTypes;
import jxcel.model.EmpDetails;
import jxcel.model.IBiometricFile;
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
public class BiometricFileWorker implements IBiometricFile {

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


            cell = sheet.getCell(13, 7);
            String monthYear = cell.getContents();
            StringTokenizer st = new StringTokenizer(monthYear, "   ");

            String tempDate = null;
            String tempString = null;


            AttendanceOfDate[] attendanceOfDate = null;

            Month month = Month.valueOf(st.nextElement().toString().toUpperCase());
            Year year = Year.parse((String) st.nextElement());

            BiometricAttendanceStatusTypes attendanceStatus = null;

            for (int i = 0; i < 91; i++) {
                details[0] = details[1] = null;

                cell = sheet.getCell(3, 13 + (18 * ADDROWSTEPS));
                details[0] = cell.getContents();

                cell = sheet.getCell(3, 15 + (18 * ADDROWSTEPS));
                details[1] = cell.getContents();

                attendanceOfDate = new AttendanceOfDate[31];

                for (int k = 0; k < 31; k++) {
                    attendanceStatus = null;
                    attendanceOfDate[k] = new AttendanceOfDate();

                    tempDate = "" + (k + 1) + "/" + month.getValue() + "/" + year;

                    attendanceOfDate[k].setCurrentDate(tempDate);

                    cell = sheet.getCell(k, 20 + (18 * ADDROWSTEPS));

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
                                    attendanceStatus = BiometricAttendanceStatusTypes.WEEKEND_HOLIDAY;
                                    break lb;
                                case "P":
                                    attendanceStatus = BiometricAttendanceStatusTypes.PRESENT;
                                    break lb;
                                default:
                                    if (j == 2)
                                        attendanceOfDate[k].setCheckIn(tempString);
                                    else if (j == 3)
                                        attendanceOfDate[k].setCheckOut(tempString);
                            }
                        }
                    }
                    attendanceOfDate[k].setBiometricAttendanceStatusTypes(attendanceStatus);
                }

                empList.add(new EmpDetails(details[0], details[1], attendanceOfDate));


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
                System.out.print(emp.attendanceOfDate[j].getCurrentDate());
                System.out.print("\tIn Time: " + emp.attendanceOfDate[j].getCheckIn());
                System.out.print("\tOut Time: " + emp.attendanceOfDate[j].getCheckOut());
                System.out.print("\tStatus: " + emp.attendanceOfDate[j].getBiometricAttendanceStatusTypes() + "\n");

                TimeManager.calculateTimeDifference(emp.attendanceOfDate[j].getCheckIn(), emp.attendanceOfDate[j].getCheckOut(), j + 1);
            }
            System.out.println();
        }
    }
}
