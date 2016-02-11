package jxcel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
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
    private int ADDROWSTEPS = 18;

    @Override
    public void readBiometricFile(String biometricFile) throws IOException {
        File inputWorkbook = new File(biometricFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet

            Sheet sheet = w.getSheet(0);

            empList = new ArrayList<>();
            Cell cell;
            String[][] details = new String[6][];
            String empTime;

            System.out.println("check 2");

            for (int i = 0; i < 72; i++) {
                cell = sheet.getCell(3, 13 + (18 * ADDROWSTEPS));
                details[0] = new String[1];
                details[0][0] = cell.getContents();

                cell = sheet.getCell(3, 15 + (18 * ADDROWSTEPS));
                details[1] = new String[1];
                details[1][0] = cell.getContents();


                String temp;
                details[2] = new String[31];
                details[3] = new String[31];
                details[4] = new String[31];
                details[5] = new String[31];


                for (int k = 0; k < 31; k++) {
                    cell = sheet.getCell(k, 20 + (18 * ADDROWSTEPS));
                    empTime = cell.getContents();
                    StringTokenizer st = new StringTokenizer(empTime, "   ");

                    for (int j = 2; j < 6; j++) {
                        if (st.hasMoreElements()) {
                            temp = (String) st.nextElement();
                            if (temp.equals("A") || temp.equals("P") || temp.equals("P/A") || temp.equals("W"))
                                details[5][k] = temp;
                            else
                                details[j][k] = temp;

                        }
                    }
                }
                empList.add(new EmpDetails(details[0][0], details[1][0], details[2], details[3], details[4], details[5]));

                ADDROWSTEPS++;
            }
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayBiometricFile() {

        iterator = empList.iterator();
        //for (int i = 0; i < 91; i++) {

        while (iterator.hasNext()) {
            EmpDetails emp = iterator.next();
            System.out.println("Name: " + emp.name);
            System.out.println("Employee ID: " + emp.empID);

            for (int j = 0; j < 31; j++) {
                System.out.print((j + 1) + " In Time: " + emp.checkIn[j]);
                System.out.print("\tOut Time: " + emp.checkOut[j]);
                System.out.print("\tStatus: " + emp.status[j] + "\n");

                TimeManager.calculateTimeDifference(emp.checkIn[j], emp.checkOut[j], j + 1);
            }
            System.out.println();
        }
    }
}
