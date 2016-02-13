package jxcel.model;

import jxcel.attendence.AttendanceOfLeave;

/**
 * Created by AroraA on 09-02-2016.
 */
public class HrnetDetails {

    public final String hrID;
    public final String name;
    public final String requestID;
    public final AttendanceOfLeave leaveDetails;


    public HrnetDetails(String hrID, String name, String requestID, AttendanceOfLeave leaveDetails) {
        this.hrID = hrID;
        this.name = name;
        this.requestID = requestID;
        this.leaveDetails = leaveDetails;
    }
}
