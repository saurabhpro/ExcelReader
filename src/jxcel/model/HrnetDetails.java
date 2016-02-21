package jxcel.model;

import jxcel.attendence.AttendanceOfLeave;

/**
 * Created by AroraA on 09-02-2016.
 */
public class HrnetDetails {

    public final String employeeID;
    public final String name;
    //public final String requestID;
    public final AttendanceOfLeave attendanceOfLeave;


    public HrnetDetails(String employeeID, String name, String requestID, AttendanceOfLeave attendanceOfLeave) {
        this.employeeID = employeeID;
        this.name = name;
        //this.requestID = requestID;
        this.attendanceOfLeave = attendanceOfLeave;
    }
}
