package jxcel;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by kumars on 2/8/2016.
 */
public class ReadExcel {

    private String inputFile;

    public static void main(String[] args) throws IOException {
        ReadExcel test = new ReadExcel();
        test.setInputFile("C:\\Users\\AroraA\\IdeaProjects\\ExcelReader\\src\\Biometric.xls");
        test.read();
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void read() throws IOException {
        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet

            Sheet sheet = w.getSheet(0);

            // System.out.println(sheet.getColumns()+" "+sheet.getRows());

            EmpDetails[] emp = new EmpDetails[91];
            Cell cell;
            String[] details = new String[6];
            String empTime;

            int addrow = 0;
            for (int i = 0; i < 91; i++) {
                cell = sheet.getCell(3, 13 + (18 * addrow));
                details[0] = cell.getContents();
                cell = sheet.getCell(3, 15 + (18 * addrow));
                details[1] = cell.getContents();
                cell = sheet.getCell(3, 20 + (18 * addrow));
                empTime = cell.getContents();
                String temp;
                StringTokenizer st = new StringTokenizer(empTime, "   ");
                for (int j = 2; j < 6; j++) {
                    if (st.hasMoreElements()) {
                        temp = (String) st.nextElement();
                        if (temp.equals("A")||temp.equals("P")||temp.equals("P/A")||temp.equals("W"))
                            details[5] = temp;
                        else
                            details[j] = temp;

                    }


                }
                emp[i] = new EmpDetails(details[0], details[1], details[2], details[3], details[4], details[5]);

               details[2]= details[3] = details[4] = details[5] = null;
                addrow++;
            }
            for (int i = 0; i < 91; i++) {
                System.out.println("Name: " + emp[i].name);
                System.out.println("Employee ID: " + emp[i].empID);
                System.out.println("Check In Time: " + emp[i].checkIn);
                System.out.println("Check Out Time: " + emp[i].checkOut);

                System.out.println("Status: " + emp[i].status + "\n");
            }



            /*for (int col = 1; col <=sheet.getColumns(); col++)
            {
                for (int row = 1; row <= sheet.getRows(); row++) {

                    if(row==14)
                    {
                        Cell cell = sheet.getCell(4, row);

                    }
                    if(row==16) {
                        Cell cell = sheet.getCell(col, row);
                        CellType type = cell.getType();
                        if (type == CellType.LABEL) {
                            System.out.print(cell.getContents() + "\t");
                        }

                        if (type == CellType.NUMBER) {
                            System.out.print(cell.getContents() + "\t");
                        }
                    }
                }

                System.out.println();
            }*/
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

}