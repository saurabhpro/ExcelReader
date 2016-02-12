package jxcel;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Saurabh on 2/10/2016.
 */
public interface IJxcelFile {
    void readBiometricFile(String x) throws IOException, ParseException;

    void displayBiometricFile();
}
