package jxcel;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by AroraA on 09-02-2016.
 */
public class HrnetDetails {
    String name;
    String hrID;
    String requestID;
    LeaveType leaveType;
    String startDate;
    String endDate;
    double absenceTime;

    HrnetDetails(String name, String hrID, String requestID, LeaveType leaveType, String startDate, String endDate, double absenceTime) {
        this.name = name;
        this.hrID = hrID;
        this.requestID = requestID;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.absenceTime = absenceTime;
    }
}
