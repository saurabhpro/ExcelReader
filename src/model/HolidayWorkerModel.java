package model;

import model.attendence.HolidaysList;

/**
 * Created by Saurabh on 3/12/2016.
 */
public class HolidayWorkerModel {
    private final BasicEmployeeDetails basicEmployeeDetails;
    private final JSONModelForWeb.SubMenuAttendanceOfDate subMenuAttendanceOfDate;
    private HolidaysList holiday;

    public HolidayWorkerModel(BasicEmployeeDetails b, JSONModelForWeb.SubMenuAttendanceOfDate s) {
        this.basicEmployeeDetails = b;
        this.subMenuAttendanceOfDate = s;
    }

    public BasicEmployeeDetails getBasicEmployeeDetails() {
        return basicEmployeeDetails;
    }

    public JSONModelForWeb.SubMenuAttendanceOfDate getSubMenuAttendanceOfDate() {
        return subMenuAttendanceOfDate;
    }

    public HolidaysList getHoliday() {
        return holiday;
    }

    public void setHoliday(HolidaysList holiday) {
        this.holiday = holiday;
    }

    public void display() {
        System.out.println();
        getBasicEmployeeDetails().displayBasicInfo();
        getSubMenuAttendanceOfDate().displaySub();
        if (holiday != null)
            System.out.println("Holiday is " + getHoliday().name());
    }
}
