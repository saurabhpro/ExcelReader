package jxcel.attendence;

import jxcel.model.BiometricAttendanceStatusTypes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static jxcel.TimeManager.calculateTimeDifference;

/**
 * Created by kumars on 2/12/2016.
 */
public class AttendanceOfDate {
    private LocalDate currentDate = null;
    private LocalTime checkIn = null;
    private LocalTime checkOut = null;
    private LocalTime overTime = null;
    private LocalTime workTimeForDay = null;
    private BiometricAttendanceStatusTypes biometricAttendanceStatusTypes = null;

    public LocalTime getWorkTimeForDay() {
        if (getCheckIn() != null && getCheckOut() != null && getCurrentDate() != null)
            workTimeForDay = calculateTimeDifference(getCheckIn(), getCheckOut(), getCurrentDate());

        return workTimeForDay;
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

    public BiometricAttendanceStatusTypes getBiometricAttendanceStatusTypes() {
        return biometricAttendanceStatusTypes;
    }

    public void setBiometricAttendanceStatusTypes(BiometricAttendanceStatusTypes biometricAttendanceStatusTypes) {
        if (biometricAttendanceStatusTypes.compareTo(BiometricAttendanceStatusTypes.PRESENT) == 0) {
            if (getWorkTimeForDay().compareTo(LocalTime.of(4, 0)) < 0)
                biometricAttendanceStatusTypes = BiometricAttendanceStatusTypes.ABSENT;
            else if (getWorkTimeForDay().compareTo(LocalTime.of(6, 0)) < 0)
                biometricAttendanceStatusTypes = BiometricAttendanceStatusTypes.HALF_DAY;
            else
                biometricAttendanceStatusTypes = BiometricAttendanceStatusTypes.PRESENT;
        }
        if (biometricAttendanceStatusTypes.compareTo(BiometricAttendanceStatusTypes.ABSENT) == 0) {
            if (getCurrentDate().getDayOfWeek() == DayOfWeek.SATURDAY || getCurrentDate().getDayOfWeek() == DayOfWeek.SUNDAY)
                biometricAttendanceStatusTypes = BiometricAttendanceStatusTypes.WEEKEND_HOLIDAY;
        }
        this.biometricAttendanceStatusTypes = biometricAttendanceStatusTypes;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate date) {
        this.currentDate = date;
    }
}
