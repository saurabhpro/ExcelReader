package jxcel;

import java.io.IOException;

/**
 * Created by Saurabh on 2/10/2016.
 */
public interface ApachePOIFile {
    void readHRNetFile(String x) throws IOException;

    void displayApachePOIFile(HrnetDetails[] x);
}
