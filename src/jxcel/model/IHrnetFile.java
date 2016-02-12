package jxcel.model;

import jxcel.model.HrnetDetails;

import java.io.IOException;

/**
 * Created by Saurabh on 2/10/2016.
 */
public interface IHrnetFile {
    void readHRNetFile(String x) throws IOException;

    void displayApachePOIFile();
}
