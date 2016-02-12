package jxcel.attendence;

import jxcel.model.LeaveTypes;

import java.util.Iterator;

/**
 * Created by kumars on 2/12/2016.
 */
public class AttendanceOfLeave {
    private LeaveTypes leaveTypes = null;
    private String startDate = null;
    private String endDate = null;
    private double absenceTime;

    public LeaveTypes getLeaveTypes() {
        return leaveTypes;
    }

    public void setLeaveTypes(LeaveTypes leaveTypes) {
        this.leaveTypes = leaveTypes;
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

    public double getAbsenceTime() {
        return absenceTime;
    }

    public void setAbsenceTime(double absenceTime) {
        this.absenceTime = absenceTime;
    }
}
