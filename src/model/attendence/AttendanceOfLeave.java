package model.attendence;

import java.time.LocalDate;

/**
 * Created by kumars on 2/12/2016.
 */
//
public class AttendanceOfLeave {
    private LeaveType leaveType = null;
    private LocalDate startDate = null;
    private LocalDate endDate = null;
    private double absenceTime;

    public double getAbsenceTime() {
        return absenceTime;
    }

    public void setAbsenceTime(double absenceTime) {
        this.absenceTime = absenceTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

}
