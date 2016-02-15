/**
 * Created by Saurabhk on 08-02-2016.
 */

package jxcel.model;

import jxcel.attendence.AttendanceOfDate;

public class BiometricDetails {
    public final String name;
    public final String empId;
    public final AttendanceOfDate[] attendanceOfDate;
    public boolean needClarificationFromEmployee = false;

    public BiometricDetails(String eName, String eID, AttendanceOfDate[] attendanceOfDate) {
        name = eName;
        empId = eID;
        this.attendanceOfDate = attendanceOfDate;
    }

    public boolean isNeedClarificationFromEmployee() {
        return needClarificationFromEmployee;
    }

    public void setIfClarificationFromEmployee(boolean needClarificationFromEmployee) {
        this.needClarificationFromEmployee = needClarificationFromEmployee;
    }
}
