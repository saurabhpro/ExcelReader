package jxcel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.StringTokenizer;

/**
 * Created by SaurabhK on 09-02-2016.
 */
public class TimeManager {

    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    public static void calculateTimeDifference(String s, String s1, int date) {
        try {


            if (s != null && s1 != null) {
                StringTokenizer st = new StringTokenizer(s, ":");
                int hr1 = Integer.parseInt((String) st.nextElement());
                int min1 = Integer.parseInt((String) st.nextElement());
                st = new StringTokenizer(s1, ":");
                int hr2 = Integer.parseInt((String) st.nextElement());
                int min2 = Integer.parseInt((String) st.nextElement());
                LocalDateTime fromDateTime = LocalDateTime.of(2016, 1, date, hr1, min1, 0);

                LocalDateTime toDateTime = LocalDateTime.of(2016, 1, date, hr2, min2, 0);

                long time[] = getTime(fromDateTime, toDateTime);

                String hours = "" + time[0];
                String minutes = "" + time[1];
                if (time[0] < 10 && time[0] >= 0)
                    hours = "0" + time[0];

                if (time[1] < 10 && time[1] >= 0)
                    minutes = "0" + time[1];
                System.out.println(hours + ":" + minutes);


            }
        } catch (Exception e) {//this generic but you can control another types of exception
        }
    }

    private static long[] getTime(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        LocalDateTime today = LocalDateTime.of(toDateTime.getYear(),
                toDateTime.getMonthValue(), toDateTime.getDayOfMonth(), fromDateTime.getHour(), fromDateTime.getMinute(), fromDateTime.getSecond());
        Duration duration = Duration.between(today, toDateTime);

        long seconds = duration.getSeconds();

        long hours = seconds / SECONDS_PER_HOUR;
        long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        long secs = (seconds % SECONDS_PER_MINUTE);

        return new long[]{hours, minutes, secs};
    }
}
