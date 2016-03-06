package factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by Saurabh on 3/3/2016.
 */
public class XLSXSheetAndCell {

	private Sheet sheet = null;

	public Sheet ApacheXLSXSheet(String empListID) {

		try {
			FileInputStream file = new FileInputStream(new File(empListID));
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheetAt(0); // Get the first sheet
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sheet;
	}
}
