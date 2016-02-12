package jxcel;

/**
 * Created by kumars on 2/12/2016.
 */
public enum BiometricAttendanceStatus {
    ABSENT("A"),
    PRESENT("P"),
    PUBLIC_HOLIDAY("PH"),
    WEEKEND_HOLIDAY("W"),;

    private String hLevel;

    BiometricAttendanceStatus(String hLevel) {
        this.hLevel = hLevel;
    }

}
