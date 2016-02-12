package jxcel.attendence;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kumars on 2/12/2016.
 */
public class AttendanceOfDate {

    private Date currentDate = null;
    private String checkIn = null;
    private String checkOut = null;
    private String overTime = null;
    private BiometricAttendanceStatus biometricAttendanceStatus = null;

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public BiometricAttendanceStatus getBiometricAttendanceStatus() {
        return biometricAttendanceStatus;
    }

    public void setBiometricAttendanceStatus(BiometricAttendanceStatus biometricAttendanceStatus) {
        this.biometricAttendanceStatus = biometricAttendanceStatus;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String date) throws ParseException {
        DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.currentDate = simpleDateFormat.parse(date);
    }
}
