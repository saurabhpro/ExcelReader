/**
 * Created by AroraA on 08-02-2016.
 */

package jxcel;

public class EmpDetails {
    String name;
    String empID;
    String[] checkIn;
    String[] checkOut;
    String[] overTime;
    String[] status;

    EmpDetails(String ename, String eID, String[] chkIn, String[] chkOut, String[] ovrTime, String[] estatus) {
        name = ename;
        empID = eID;
        checkIn = chkIn;
        checkOut = chkOut;
        overTime=ovrTime;
        status = estatus;
    }

}
