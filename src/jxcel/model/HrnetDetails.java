package jxcel.model;

import jxcel.attendence.SalesforceLeave;

/**
 * Created by AroraA on 09-02-2016.
 */
public class HrnetDetails {
    public String name;
    public String hrID;
    public String requestID;
    public SalesforceLeave leaveType;


    public HrnetDetails(String name, String hrID, String requestID, SalesforceLeave leaveType) {
        this.name = name;
        this.hrID = hrID;
        this.requestID = requestID;
        this.leaveType = leaveType;
    }
}
