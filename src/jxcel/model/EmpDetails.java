/**
 * Created by Saurabhk on 08-02-2016.
 */

package jxcel.model;

import jxcel.attendence.AttendanceOfDate;

public class EmpDetails {
    public String name;
    public String empId;
    public AttendanceOfDate[] attendanceOfDate;

    public EmpDetails(String eName, String eID, AttendanceOfDate[] attendanceOfDate) {
        name = eName;
        empId = eID;
        this.attendanceOfDate = attendanceOfDate;
    }


}
