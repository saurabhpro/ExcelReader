package jxcel.attendence;

import jxcel.model.LeaveTypes;

import java.time.LocalDate;

/**
 * Created by kumars on 2/12/2016.
 */
public class AttendanceOfLeave {
    private LeaveTypes leaveTypes = null;
    private LocalDate startDate = null;
    private LocalDate endDate = null;
    private double absenceTime;

    public LeaveTypes getLeaveTypes() {
        return leaveTypes;
    }

    public void setLeaveTypes(LeaveTypes leaveTypes) {
        this.leaveTypes = leaveTypes;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getAbsenceTime() {
        return absenceTime;
    }

    public void setAbsenceTime(double absenceTime) {
        this.absenceTime = absenceTime;
    }
}
