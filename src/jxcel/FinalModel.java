package jxcel;

import jxcel.attendence.AttendanceOfDate;
import jxcel.attendence.AttendanceOfLeave;
import jxcel.model.HrnetDetails;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kumars on 2/19/2016.
 */
public class FinalModel {
    public final String employeeID;
    public final String name;
    public final AttendanceOfDate[] attendanceOfDate;
    public int numberOfLeaves;
    public boolean needClarificationFromEmployee = false;
    ArrayList<HrnetDetails> hrnetDetails = new ArrayList<>();

    public FinalModel(String employeeID, String name, AttendanceOfDate[] attendanceOfDate, int numberOfLeaves, ArrayList<HrnetDetails> hrnetDetails) {
        this.employeeID = employeeID;
        this.name = name;
        this.attendanceOfDate = attendanceOfDate;
        this.numberOfLeaves = numberOfLeaves;
        this.hrnetDetails = hrnetDetails;
    }
    /*public FinalModel(List<EmpBiometricDetails> biometricDetails, List<HrnetDetails> hrnetDetails) {
        this.employeeID = biometricDetails.g;
        this.name = name;
        this.requestID = requestID;
        this.attendanceOfLeave = attendanceOfLeave;
        this.attendanceOfDate = attendanceOfDate;
    }*/
    public void displayArrayList() {
        Iterator<HrnetDetails> iterator = hrnetDetails.iterator();
        while (iterator.hasNext()) {
            HrnetDetails hr = iterator.next();

            System.out.print(hr.employeeID);
            System.out.print("\t" + hr.name);
            System.out.print("\t" + hr.requestID);
            System.out.print("\t" + hr.attendanceOfLeave.getLeaveType());
            System.out.print("\t" + hr.attendanceOfLeave.getStartDate() + "\t" + hr.attendanceOfLeave.getEndDate());
            System.out.println("\t" + hr.attendanceOfLeave.getAbsenceTime());

        }
    }
    public boolean isNeedClarificationFromEmployee() {
        return needClarificationFromEmployee;
    }

    public void setIfClarificationFromEmployee(boolean needClarificationFromEmployee) {
        this.needClarificationFromEmployee = needClarificationFromEmployee;
    }
}
