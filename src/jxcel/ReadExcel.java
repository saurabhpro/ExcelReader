package jxcel;

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
        JxcelFileWorker jxcelFileWorker = new JxcelFileWorker();
        jxcelFileWorker.readBiometricFile(biometricFile);
        jxcelFileWorker.displayBiometricFile();


        System.out.println("check 1");
        test.setHrNetFile(".\\ExcelFiles\\HRNet.xlsx");
        ApacheFileWorker apacheFileWorker = new ApacheFileWorker();
        apacheFileWorker.readHRNetFile(hrNetFile);
        apacheFileWorker.displayApachePOIFile(apacheFileWorker.hrnetDetails);

    }

    public void setBiometricFile(String biometricFile) {
        this.biometricFile = biometricFile;
    }

    public void setHrNetFile(String hrNetFile) {
        this.hrNetFile = hrNetFile;
    }


}

