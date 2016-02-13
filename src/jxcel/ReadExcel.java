package jxcel;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jxcel.model.EmpDetails;
import jxcel.view.JsonMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by kumars on 2/8/2016.
 */
public class ReadExcel {
    private static String biometricFile;
    private static String hrNetFile;

    public static void main(String[] args) throws IOException, ParseException {
        ReadExcel test = new ReadExcel();

        test.setBiometricFile(".\\ExcelFiles\\Biometric.xls");
        BiometricFileWorker jxcelFileWorker = new BiometricFileWorker();
        jxcelFileWorker.readBiometricFile(biometricFile);
        jxcelFileWorker.displayBiometricFile();


        System.out.println("check 1");
        test.setHrNetFile(".\\ExcelFiles\\HRNet.xlsx");
        HrnetFileWorker hrnetFileWorker = new HrnetFileWorker();
        hrnetFileWorker.readHRNetFile(hrNetFile);
        hrnetFileWorker.displayApachePOIFile();

        new JsonMapper().toJsonFile(null).fromJsonToFormattedJson(null);


    }

    public void setBiometricFile(String biometricFile) {
        this.biometricFile = biometricFile;
    }

    public void setHrNetFile(String hrNetFile) {
        this.hrNetFile = hrNetFile;
    }


}

