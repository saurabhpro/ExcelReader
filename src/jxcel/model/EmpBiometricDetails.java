/**
 * Created by Saurabhk on 08-02-2016.
 */

package jxcel.model;

import jxcel.attendence.AttendanceOfDate;

public class EmpBiometricDetails {
    public final String name;
    public final String empId;
    public final AttendanceOfDate[] attendanceOfDate;
    public int numberOfLeaves = 0; //To check how many leaves have been applied

    public EmpBiometricDetails(String eID, String eName, AttendanceOfDate[] attendanceOfDate) {
        name = eName;
        empId = eID;
        this.attendanceOfDate = attendanceOfDate;
    }

    /* public void setNumberOfLeaves() {
         numberOfLeaves = numberOfLeaves + 1;
     } //Value is set in Combined2.java file
 */
    public int getNumberOfLeaves() {
        return numberOfLeaves;
    }

    public void setNumberOfLeaves(int numberOfLeaves) {
        this.numberOfLeaves = numberOfLeaves;
    }


}
