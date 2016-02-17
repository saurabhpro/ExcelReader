package jxcel.model;

import java.time.LocalDate;
import java.time.Month;

/**
 * Created by kumars on 2/16/2016.
 */

public enum HolidaysList {
    NEW_YEAR_DAY(LocalDate.of(2016, Month.JANUARY, 1)),
    REPUBLIC_DAY(LocalDate.of(2016, Month.JANUARY, 26)),
    HOLI(LocalDate.of(2016, Month.MARCH, 24)),
    GOOD_FRIDAY(LocalDate.of(2016, Month.MARCH, 25)),
    INDEPENDENCE_DAY(LocalDate.of(2016, Month.AUGUST, 15)),
    RAKSHABANDHAN(LocalDate.of(2016, Month.AUGUST, 18)),
    VIJAY_DASHMI(LocalDate.of(2016, Month.OCTOBER, 11)),
    DAY_AFTER_DIWALI(LocalDate.of(2016, Month.OCTOBER, 31)),
    GURU_NANAK_JAYANTI(LocalDate.of(2016, Month.NOVEMBER, 14)),
    DAY_AFTER_CHRISTMAS(LocalDate.of(2016, Month.DECEMBER, 26));

    private LocalDate date;

    HolidaysList(LocalDate of) {
        this.date = of;
    }

    public LocalDate getDate() {
        return date;
    }
}
