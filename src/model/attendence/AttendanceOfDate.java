package model.attendence;

import static jxcel.TimeManager.calculateTimeDifference;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by kumars on 2/12/2016.
 */
public class AttendanceOfDate {
	private LocalDate currentDate = null;
	private LocalTime checkIn = null;
	private LocalTime checkOut = null;
	private LocalTime overTime = null;
	private LocalTime workTimeForDay = null;
	private AttendanceStatusType attendanceStatusType = null;

	public AttendanceStatusType getAttendanceStatusType() {
		return attendanceStatusType;
	}

	public LocalTime getCheckIn() {
		return checkIn;
	}

	public LocalTime getCheckOut() {
		return checkOut;
	}

	public LocalDate getCurrentDate() {
		return currentDate;
	}

	public LocalTime getOverTime() {
		return overTime;
	}

	public LocalTime getWorkTimeForDay() {
		if (workTimeForDay == null && getCheckIn() != null && getCheckOut() != null && getCurrentDate() != null)
			workTimeForDay = calculateTimeDifference(getCheckIn(), getCheckOut(), getCurrentDate());

		return workTimeForDay;
	}

	public void setAttendanceStatusType(AttendanceStatusType statusType) {
		if (statusType.compareTo(AttendanceStatusType.PRESENT) == 0) {
			if (getWorkTimeForDay().compareTo(LocalTime.of(4, 0)) < 0)
				statusType = AttendanceStatusType.ABSENT;
			else if (getWorkTimeForDay().compareTo(LocalTime.of(6, 0)) < 0)
				statusType = AttendanceStatusType.HALF_DAY;
		}

		if (statusType.compareTo(AttendanceStatusType.ABSENT) == 0) {
			if (getCurrentDate().getDayOfWeek() == DayOfWeek.SATURDAY
					|| getCurrentDate().getDayOfWeek() == DayOfWeek.SUNDAY)

				statusType = AttendanceStatusType.WEEKEND_HOLIDAY;
		}
		this.attendanceStatusType = statusType;
	}

	public void setCheckIn(LocalTime checkIn) {
		this.checkIn = checkIn;
	}

	public void setCheckOut(LocalTime checkOut) {
		this.checkOut = checkOut;
	}

	public void setCurrentDate(LocalDate date) {
		this.currentDate = date;
	}

	public void setOverTime(LocalTime overTime) {
		this.overTime = overTime;
	}

	public void setWorkTimeForDay(LocalTime workTimeForDay) {
		this.workTimeForDay = workTimeForDay;
	}
}
