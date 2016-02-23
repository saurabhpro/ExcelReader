package jxcel.model;

import jxcel.attendence.AttendanceOfDate;
import jxcel.attendence.AttendanceOfLeave;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumars on 2/19/2016.
 */
public class FinalModel {
    public final String employeeID;
    public final String name;
    public final AttendanceOfDate[] attendanceOfDate;
    public boolean needClarificationFromEmployee = false;
    List<HrnetDetails> hrnetDetails = new ArrayList<>();

    public FinalModel(String employeeID, String name, AttendanceOfDate[] attendanceOfDate, ArrayList<HrnetDetails> hrnetDetails) {
        this.employeeID = employeeID;
        this.name = name;
        this.attendanceOfDate = attendanceOfDate;
        this.hrnetDetails = hrnetDetails;
    }

    /*public FinalModel(List<EmpBiometricDetails> biometricDetails, List<HrnetDetails> hrnetDetails) {
        this.employeeID = biometricDetails.g;
        this.name = name;
        this.requestID = requestID;
        this.attendanceOfLeave = attendanceOfLeave;
        this.attendanceOfDate = attendanceOfDate;
    }*/

    public boolean isNeedClarificationFromEmployee() {
        return needClarificationFromEmployee;
    }

    public void setIfClarificationFromEmployee(boolean needClarificationFromEmployee) {
        this.needClarificationFromEmployee = needClarificationFromEmployee;
    }
}
