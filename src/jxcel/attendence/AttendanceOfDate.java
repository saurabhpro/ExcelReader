package jxcel.attendence;

import jxcel.model.AttendanceStatusType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static jxcel.TimeManager.calculateTimeDifference;
import static jxcel.model.AttendanceStatusType.*;

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

    public LocalTime getWorkTimeForDay() {
        if (getCheckIn() != null && getCheckOut() != null && getCurrentDate() != null)
            workTimeForDay = calculateTimeDifference(getCheckIn(), getCheckOut(), getCurrentDate());

        return workTimeForDay;
    }

    public void setWorkTimeForDay(LocalTime workTimeForDay) {
        this.workTimeForDay = workTimeForDay;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }

    public LocalTime getOverTime() {
        return overTime;
    }

    public void setOverTime(LocalTime overTime) {
        this.overTime = overTime;
    }

    public AttendanceStatusType getAttendanceStatusType() {
        return attendanceStatusType;
    }

    public void setAttendanceStatusType(AttendanceStatusType statusType) {
        if (statusType.compareTo(PRESENT) == 0) {
            if (getWorkTimeForDay().compareTo(LocalTime.of(4, 0)) < 0)
                statusType = ABSENT;
            else if (getWorkTimeForDay().compareTo(LocalTime.of(6, 0)) < 0)
                statusType = HALF_DAY;
            else
                statusType = PRESENT;
        }
        if (statusType.compareTo(ABSENT) == 0 || statusType.compareTo(NOT_AN_EMPLOYEE) == 0) {
            if (getCurrentDate().getDayOfWeek() == DayOfWeek.SATURDAY ||
                    getCurrentDate().getDayOfWeek() == DayOfWeek.SUNDAY)
                statusType = WEEKEND_HOLIDAY;
        }
        this.attendanceStatusType = statusType;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate date) {
        this.currentDate = date;
    }
}
