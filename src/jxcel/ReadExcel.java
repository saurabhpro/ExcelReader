package jxcel;

import DataUsage.EmployeeMasterData;
import combinedModel.Combined2;
import combinedModel.Discrepancy;
import combinedModel.PublicHolidayList;
import jxcel.factory.SheetFactory;
import jxcel.view.JsonMapper;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by kumars on 2/8/2016.
 */
public class ReadExcel {
    private static String biometricFile;
    private static String hrNetFile;
    private static String empListID;

    public static void main(String[] args) throws IOException, ParseException {
        ReadExcel test = new ReadExcel();
        test.setEmpListID(".\\ExcelFiles\\Emails.xlsx");
        test.setBiometricFile(".\\ExcelFiles\\jan leaves.xls");
        test.setHrNetFile(".\\ExcelFiles\\Jan-Feb FF Report.xlsx");

        EmployeeMasterData employeeMasterData = new EmployeeMasterData(empListID);
        employeeMasterData.readFile();
        // employeeMasterData.displayFile();
        //employeeMasterData.toJsonFile();

        SheetFactory sheetFactory = new SheetFactory();


        //read Biometric Excel File
        Object fileWorker = sheetFactory.dispatch("Jxcel", biometricFile);
        if (fileWorker instanceof JxcelBiometricFileWorker) {
            ((JxcelBiometricFileWorker) fileWorker).readBiometricFile();
            //  ((JxcelBiometricFileWorker) fileWorker).displayBiometricFile();
        }

        //read HRNet Excel File
        fileWorker = sheetFactory.dispatch("XLSX", hrNetFile);
        if (fileWorker instanceof HrnetFileWorker) {
            ((HrnetFileWorker) fileWorker).readHRNetFile();
            //   ((HrnetFileWorker) fileWorker).displayHRNetFile();
        }

        Combined2 combined2 = new Combined2();
        combined2.combineFiles();
        new JsonMapper().toJsonFile(null).fromJsonToFormattedJson(null);

        //display Combined Files
        combined2.displayCombineFiles();

        // remove discrepancies
        Discrepancy discrepancy = new Discrepancy();
        discrepancy.findDiscrepancy();

        new PublicHolidayList().presentPublicHolidayList();
    }

    public void setEmpListID(String empListID) {
        ReadExcel.empListID = empListID;
    }

    public void setBiometricFile(String biometricFile) {
        ReadExcel.biometricFile = biometricFile;
    }

    public void setHrNetFile(String hrNetFile) {
        ReadExcel.hrNetFile = hrNetFile;
    }


}

