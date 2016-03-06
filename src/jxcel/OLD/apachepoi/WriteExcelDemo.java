package jxcel.OLD.apachepoi;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by kumars on 2/3/2016.
 */

// import statements
public class WriteExcelDemo {
	public static void main(String... args) {
		// Blank workbook
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Employee Data");

		// This data needs to be written (Object[])
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] { "ID", "NAME", "LASTNAME" });
		data.put("2", new Object[] { 1, "Amrita", "Arora" });
		data.put("3", new Object[] { 2, "Saurabh", "Kumar" });
		data.put("4", new Object[] { 3, "Harshit", "Bhatia" });
		data.put("5", new Object[] { 4, "Surabhi", "Kulkarni" });

		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellNum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellNum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(".\\ExcelFiles\\MyExcelPOI.xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("MyExcelPOI.xlsx written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}