package jxcel;

import jxl.read.biff.BiffException;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Date;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * Created by kumars on 2/8/2016.
 */
public class ReadExcel {


    private String biometricFile;
    private String hrNetFile;

    public static void main(String[] args) throws IOException {
        ReadExcel test = new ReadExcel();

        test.setBiometricFile("C:\\Users\\AroraA\\IdeaProjects\\ExcelReader\\src\\Biometric.xls");
        test.readBiometricFile();

        test.setHrNetFile("C:\\Users\\AroraA\\IdeaProjects\\ExcelReader\\ExcelFiles\\HRNet.xlsx");
        test.readHRNetFile();

    }

    public void setBiometricFile(String biometricFile) {
        this.biometricFile = biometricFile;
    }

    public void setHrNetFile(String hrNetFile) {
        this.hrNetFile = hrNetFile;
    }

    private void readHRNetFile() throws IOException {
        FileInputStream file = new FileInputStream(new File(hrNetFile));
        //Create Workbook instance holding reference to .xlsx file

        org.apache.poi.ss.usermodel.Workbook workbook = new XSSFWorkbook(file);

        //Get first/desired sheet from the workbook
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

        Cell cell = null;

        int numberOfRowsInHr = sheet.getPhysicalNumberOfRows();
        HrnetDetails[] hrnetDetails = new HrnetDetails[numberOfRowsInHr];

        String[] details = new String[7];
        for (int i = 1; i < numberOfRowsInHr; i++) {
            for (int j = 0; j < 7; j++) {
                //Update the value of cell
                cell = sheet.getRow(i).getCell(j);
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        int temp = (int) cell.getNumericCellValue();
                        /*Date dt = new Date(temp);

                        if(temp >40000){
                            //details[j] = getJavaDate();
                        }
                        */
                        details[j] = Objects.toString(cell.getNumericCellValue());

                        break;
                    case Cell.CELL_TYPE_STRING:
                        details[j] = cell.getStringCellValue();
                        break;
                }
                //  System.out.println(details[j]);

            }
            hrnetDetails[i - 1] = new HrnetDetails(details[1], details[0], details[2], details[3], details[4], details[5], details[6]);
        }

        for (int i = 0; i < numberOfRowsInHr - 1; i++) {
            System.out.print(hrnetDetails[i].hrID);
            System.out.print("\t" + hrnetDetails[i].name);
            System.out.print("\t" + hrnetDetails[i].requestID);
            System.out.print("\t" + hrnetDetails[i].leaveType);
            System.out.print("\t" + hrnetDetails[i].startDate + "\t" + hrnetDetails[i].endDate);
            System.out.println("\t" + hrnetDetails[i].absenceTime);

        }
        file.close();
    }


    public void readBiometricFile() throws IOException {
        File inputWorkbook = new File(biometricFile);
        jxl.Workbook w;
        try {
            w = jxl.Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet

            jxl.Sheet sheet = w.getSheet(0);

            // System.out.println(sheet.getColumns()+" "+sheet.getRows());

            EmpDetails[] emp = new EmpDetails[91];
            jxl.Cell cell;
            String[][] details = new String[6][];
            String empTime;

            int addrow = 0;
            for (int i = 0; i < 91; i++) {
                cell = sheet.getCell(3, 13 + (18 * addrow));
                details[0] = new String[1];
                details[0][0] = cell.getContents();

                cell = sheet.getCell(3, 15 + (18 * addrow));
                details[1] = new String[1];
                details[1][0] = cell.getContents();


                String temp;
                details[2] = new String[31];
                details[3] = new String[31];
                details[4] = new String[31];
                details[5] = new String[31];


                for (int k = 0; k < 31; k++) {
                    cell = sheet.getCell(k, 20 + (18 * addrow));
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
                emp[i] = new EmpDetails(details[0][0], details[1][0], details[2], details[3], details[4], details[5]);
                addrow++;
            }
            for (int i = 0; i < 91; i++) {
                System.out.println("Name: " + emp[i].name);
                System.out.println("Employee ID: " + emp[i].empID);

                for (int j = 0; j < 31; j++) {
                    System.out.print((j + 1) + " In Time: " + emp[i].checkIn[j]);
                    System.out.print("\tOut Time: " + emp[i].checkOut[j]);
                    System.out.print("\tStatus: " + emp[i].status[j] + "\n");

                    TimeManager.calculateTimeDifference(emp[i].checkIn[j], emp[i].checkOut[j], j + 1);
                }
                System.out.println();
            }

        } catch (BiffException e) {
            e.printStackTrace();
        }
    }


}

