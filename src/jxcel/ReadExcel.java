package jxcel;

import DataUsage.ReadAndKeepData;
import combinedModel.Combined2;
import combinedModel.Discrepancy;
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
        ReadAndKeepData readAndKeepData = new ReadAndKeepData(empListID);
        readAndKeepData.readFile();
        // readAndKeepData.displayFile();
        readAndKeepData.toJsonFile();

        //read Biometric Excel File
        test.setBiometricFile(".\\ExcelFiles\\feb leaves.xls");
        JxcelBiometricFileWorker jxcelFileWorker = new JxcelBiometricFileWorker(biometricFile);
        jxcelFileWorker.readBiometricFile();

        //read HRNet Excel File
        test.setHrNetFile(".\\ExcelFiles\\Jan-Feb FF Report.xlsx");
        HrnetFileWorker hrnetFileWorker = new HrnetFileWorker(hrNetFile);
        hrnetFileWorker.readHRNetFile();

        Combined2 combined2 = new Combined2();
        combined2.combineFiles();
        new JsonMapper().toJsonFile(null).fromJsonToFormattedJson(null);

        //display Combined Files
        combined2.displayCombineFiles();

        // remove discrepancies
        Discrepancy discrepancy = new Discrepancy();
        discrepancy.findDiscrepancy();


        //display Biometric and HRNet Excel Files
        //hrnetFileWorker.displayHRNetFile();
        //jxcelFileWorker.displayBiometricFile();

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

