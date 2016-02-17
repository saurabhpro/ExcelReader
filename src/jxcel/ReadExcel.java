package jxcel;

import combinedModel.Combined2;
import jxcel.view.JsonMapper;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by kumars on 2/8/2016.
 */
public class ReadExcel {
    private static String biometricFile;
    private static String hrNetFile;

    public static void main(String[] args) throws IOException, ParseException {
        ReadExcel test = new ReadExcel();

        test.setBiometricFile(".\\ExcelFiles\\Biometric.xls");
        BiometricFileWorker jxcelFileWorker = new BiometricFileWorker(biometricFile);
        jxcelFileWorker.readBiometricFile();


        System.out.println("check 1");
        test.setHrNetFile(".\\ExcelFiles\\HRNet.xlsx");
        HrnetFileWorker hrnetFileWorker = new HrnetFileWorker(hrNetFile);
        hrnetFileWorker.readHRNetFile();
        hrnetFileWorker.displayApachePOIFile();


        new Combined2().combineFiles();
        new JsonMapper().toJsonFile(null).fromJsonToFormattedJson(null);
        jxcelFileWorker.displayBiometricFile();


    }

    public void setBiometricFile(String biometricFile) {
        ReadExcel.biometricFile = biometricFile;
    }

    public void setHrNetFile(String hrNetFile) {
        ReadExcel.hrNetFile = hrNetFile;
    }


}

