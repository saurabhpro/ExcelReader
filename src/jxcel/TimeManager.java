package jxcel;

import model.attendence.AttendanceOfDate;
import model.attendence.AttendanceStatusType;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static model.attendence.AttendanceStatusType.*;

/**
 * Created by SaurabhK on 09-02-2016.
 */
public class TimeManager {

    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    public static LocalTime calculateTimeDifference(LocalTime checkInTime, LocalTime checkOutTime, LocalDate date) {
        LocalDate froDate, toDate;
        froDate = toDate = date;
        LocalTime time = null;

        if (checkOutTime.compareTo(checkInTime) < 0)
            toDate = froDate.plusDays(1);

        LocalDateTime fromDateTime = LocalDateTime.of(froDate, checkInTime);
        LocalDateTime toDateTime = LocalDateTime.of(toDate, checkOutTime);

        time = getTime(fromDateTime, toDateTime);


        return time;
    }

    private static LocalTime getTime(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Duration duration = Duration.between(fromDateTime, toDateTime);

        long seconds = duration.getSeconds();

        long hours = seconds / SECONDS_PER_HOUR;
        long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        //long secs = (seconds % SECONDS_PER_MINUTE);

        return convertToTime(hours, minutes);

    }


    public static LocalDate convertToLocalDate(String date) {
        int year;
        int month;
        int day;

        String[] st = date.split("/");

        month = Integer.parseInt(st[0]);
        day = Integer.parseInt(st[1]);
        year = Integer.parseInt(st[2]);


        return LocalDate.of(year, month, day);
    }

    private static LocalTime convertToTime(long hr, long min) {
        return LocalTime.of((int) hr, (int) min);
    }


    public static LocalTime calculate(String type, AttendanceOfDate[] attendanceOfDate) {
        List<AttendanceOfDate> ofDates = Arrays.asList(attendanceOfDate);
        int hoursTotal = 0, minsTotal = 0, presentDays = 0;

        for (AttendanceOfDate inTime : ofDates) {
            AttendanceStatusType attendanceStatusType = inTime.getAttendanceStatusType();
            if ((attendanceStatusType.equals(ABSENT)) ||
                    ((attendanceStatusType.equals(PUBLIC_HOLIDAY) ||
                            attendanceStatusType.equals(WEEKEND_HOLIDAY))
                            && (inTime.getCheckIn() != null))) {
                switch (type) {
                    case "AverageCheckOutTime":
                        if (inTime.getCheckOut() != null) {
                            minsTotal += inTime.getCheckOut().getHour() * 60;
                            minsTotal += inTime.getCheckOut().getMinute();
                            presentDays++;
                        }
                        break;

                    case "AverageCheckInTime":
                        if (inTime.getCheckIn() != null) {
                            minsTotal += inTime.getCheckIn().getHour() * 60;
                            minsTotal += inTime.getCheckIn().getMinute();
                            presentDays++;
                        }
                        break;
                }

            }
        }
        int t = presentDays > 0 ? presentDays : 1;
        minsTotal = minsTotal / t;

        hoursTotal = minsTotal / 60;
        minsTotal = minsTotal % 60;
        return LocalTime.of(hoursTotal, minsTotal);
    }
}
