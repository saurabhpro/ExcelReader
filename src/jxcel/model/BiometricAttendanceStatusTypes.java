package jxcel.model;

/**
 * Created by kumars on 2/12/2016.
 */
public enum BiometricAttendanceStatusTypes {
    ABSENT("A"),
    PRESENT("P"),
    PUBLIC_HOLIDAY("PH"),
    WEEKEND_HOLIDAY("W"),;

    private String hLevel;

    BiometricAttendanceStatusTypes(String hLevel) {
        this.hLevel = hLevel;
    }

}
