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

	public LocalDate getEndDate() {
		return endDate;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setAbsenceTime(double absenceTime) {
		this.absenceTime = absenceTime;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

}
