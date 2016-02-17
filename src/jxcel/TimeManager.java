package jxcel;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.StringTokenizer;

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

    @NotNull
    public static LocalDate convertToLocalDate(String date) {
        int year;
        int month;
        int day;

        StringTokenizer st = new StringTokenizer(date, "/");

        month = Integer.parseInt((String) st.nextElement());
        day = Integer.parseInt((String) st.nextElement());
        year = Integer.parseInt((String) st.nextElement());


        return LocalDate.of(year, month, day);
    }

    private static LocalTime convertToTime(long hr, long min) {
        return LocalTime.of((int) hr, (int) min);
    }
}
