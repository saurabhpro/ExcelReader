package jxcel.attendence;

import jxcel.LeaveType;

/**
 * Created by kumars on 2/12/2016.
 */
public class SalesforceLeave {
    LeaveType leaveType = null;
    String startDate = null;
    String endDate = null;
    String absenceTime = null;

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAbsenceTime() {
        return absenceTime;
    }

    public void setAbsenceTime(String absenceTime) {
        this.absenceTime = absenceTime;
    }
}
