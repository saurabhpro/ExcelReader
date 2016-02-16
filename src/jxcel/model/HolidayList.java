package jxcel.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kumars on 2/16/2016.
 */
public class HolidayList {

    Map<String, LocalDate> map = new HashMap<>();

    HolidayList() {
        LocalDate[] date = {LocalDate.of(2016, Month.JANUARY, 1),
                LocalDate.of(2016, Month.JANUARY, 26),
                LocalDate.of(2016, Month.MARCH, 24),
                LocalDate.of(2016, Month.MARCH, 25),
                LocalDate.of(2016, Month.AUGUST, 15),
                LocalDate.of(2016, Month.AUGUST, 18),
                LocalDate.of(2016, Month.OCTOBER, 11),
                LocalDate.of(2016, Month.OCTOBER, 31),
                LocalDate.of(2016, Month.NOVEMBER, 14),
                LocalDate.of(2016, Month.DECEMBER, 26)};

        String[] holidayName = {"NEW_YEAR_DAY", "REPUBLIC_DAY", "HOLI",
                "GOOD_FRIDAY", "INDEPENDENCE_DAY", "RAKSHABANDHAN",
                "VIJAY_DASHMI", "DAY_AFTER_DIWALI", "GURU_NANAK_JAYANTI",
                "DAY_AFTER_CHRISTMAS"};

        populateMap(date, holidayName);
    }

    private void populateMap(LocalDate[] date, String[] holidayName) {

        int i = 0;
        for (LocalDate d : date) {
            map.put(holidayName[i++], d);
        }
    }


}
