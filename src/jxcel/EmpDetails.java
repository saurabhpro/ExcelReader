/**
 * Created by Saurabhk on 08-02-2016.
 */

package jxcel;

import jxcel.attendence.AttendanceOfDate;

public class EmpDetails {
    String name;
    String empId;
    AttendanceOfDate[] attendanceOfDate;

    public EmpDetails(String eName, String eID, AttendanceOfDate[] attendanceOfDate) {
        name = eName;
        empId = eID;
        this.attendanceOfDate = attendanceOfDate;
    }


}
