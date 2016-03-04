package jxcel.factory;

import jxcel.ApacheBiometricFileWorker;
import jxcel.HrnetFileWorker;
import jxcel.JxcelBiometricFileWorker;

/**
 * Created by Saurabh on 3/3/2016.
 */
public class SheetFactory {
    public Object dispatch(String type, String file) {

        switch (type) {
            case "Jxcel":
                return new JxcelBiometricFileWorker(file);
            case "XLS":
                return new ApacheBiometricFileWorker(file);
            case "XLSX":
                return new HrnetFileWorker(file);
        }
        return null;
    }

}
