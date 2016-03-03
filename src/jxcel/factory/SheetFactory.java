package jxcel.factory;

/**
 * Created by Saurabh on 3/3/2016.
 */
public class SheetFactory {
    public void dispatch(String type, String file) {

        switch (type) {
            case "Jxcel":
                new JXLSSheetAndCell().JXLSSheet(file);
                break;
            case "XLS":
                new XLSSheetAndCell().ApacheXLSSheet(file);
                break;
            case "XLSX":
                new XLSXSheetAndCell().ApacheXLSXSheet(file);
                break;
        }
    }

}
