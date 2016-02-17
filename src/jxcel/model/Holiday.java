package jxcel.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class Holiday {

    Map<String, LocalDate> map = new HashMap<>();

    public Holiday() {


        String[] holidayName = {"NEW_YEAR_DAY", "REPUBLIC_DAY", "HOLI",
                "GOOD_FRIDAY", "INDEPENDENCE_DAY", "RAKSHABANDHAN",
                "VIJAY_DASHMI", "DAY_AFTER_DIWALI", "GURU_NANAK_JAYANTI",
                "DAY_AFTER_CHRISTMAS"};

        //  populateMap(date, holidayName);
    }

    private void populateMap(LocalDate[] date, String[] holidayName) {

        int i = 0;
        for (LocalDate d : date) {
            map.put(holidayName[i++], d);
        }
    }
}
