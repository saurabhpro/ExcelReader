package DataUsage;

import com.fasterxml.jackson.databind.ObjectMapper;
import jxcel.model.BasicEmployeeDetails;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by kumars on 3/1/2016.
 */
public class ReadAndKeepData {

    int numberOfRowsInBio;
    FileInputStream inputWorkbook = null;
    Workbook workbook = null;
    Sheet sheet = null;
    Cell cell = null;
    Map<String, BasicEmployeeDetails> basicMap;

    public ReadAndKeepData(String empListID) {
        try {
            inputWorkbook = new FileInputStream(empListID);

            workbook = new XSSFWorkbook(inputWorkbook);
            sheet = workbook.getSheetAt(0);          // Get the first sheet
            numberOfRowsInBio = sheet.getPhysicalNumberOfRows();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile() {
        basicMap = new TreeMap<>();

        for (int row = 0; row < numberOfRowsInBio; row++) {
            BasicEmployeeDetails b = new BasicEmployeeDetails();
            b.setName(sheet.getRow(row).getCell(0).getStringCellValue());
            b.setEmailId(sheet.getRow(row).getCell(1).getStringCellValue());
            b.setEmpId(sheet.getRow(row).getCell(2).getStringCellValue());
            b.setSalesForceId((int) sheet.getRow(row).getCell(3).getNumericCellValue());

            basicMap.put(b.getEmpId(), b);
        }
    }

    public void displayFile() {
        basicMap.values().forEach(BasicEmployeeDetails::displayBasicInfo);
    }

    public void toJsonFile() {
        ObjectMapper mapper = new ObjectMapper();
        //For testing
        Map<String, BasicEmployeeDetails> user = basicMap;

        try {
            File jfile = new File(".\\JSON files\\Emails.json");
            //Convert object to JSON string and save into file directly
            mapper.writeValue(jfile, user);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
