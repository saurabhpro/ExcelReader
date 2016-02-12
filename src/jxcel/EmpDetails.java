/**
 * Created by AroraA on 08-02-2016.
 */

package jxcel;

public class EmpDetails {
    String name;
    String empId;
    AttendanceDate[] attendanceDate;

    EmpDetails(String eName, String eID, AttendanceDate[] attendanceDate) {
        name = eName;
        empId = eID;
        this.attendanceDate = attendanceDate;
    }

}
