package model;

import model.attendence.HolidaysList;

/**
 * Created by Saurabh on 3/12/2016.
 */
public class HolidayWorkerModel {
    HolidaysList holiday;
    BasicEmployeeDetails basicEmployeeDetails;
    JSONModelForWeb.SubMenuAttendanceOfDate subMenuAttendanceOfDate;

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
        basicEmployeeDetails.displayBasicInfo();
        subMenuAttendanceOfDate.displaySub();
        if (holiday != null)
            System.out.println("Holiday is " + holiday.name());
    }
}
