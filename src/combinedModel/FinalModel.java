package combinedModel;

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
    public String employeeID;
    public String name;
    public AttendanceOfDate[] attendanceOfDate;
    public boolean needClarificationFromEmployee;
    public int numberOfLeaves;
    List<HrnetDetails> empList1 = new ArrayList<>();

    public FinalModel(String employeeID, String name, int numberOfLeaves, AttendanceOfDate[] attendanceOfDate, boolean needClarificationFromEmployee, List<HrnetDetails> empList1) {
        this.employeeID = employeeID;
        this.name = name;
        this.attendanceOfDate = attendanceOfDate;
        this.empList1 = empList1;
        this.numberOfLeaves = numberOfLeaves;
        this.needClarificationFromEmployee = needClarificationFromEmployee;
    }

    public void displayArrayList() {
        Iterator<HrnetDetails> iterator = empList1.iterator();
        while (iterator.hasNext()) {
            HrnetDetails hr = iterator.next();

            System.out.print(hr.employeeID);
            System.out.print("\t" + hr.name);
            System.out.print("\t" + hr.requestID);
            System.out.print("\t" + hr.attendanceOfLeave.getLeaveTypes());
            System.out.print("\t" + hr.attendanceOfLeave.getStartDate() + "\t" + hr.attendanceOfLeave.getEndDate());
            System.out.println("\t" + hr.attendanceOfLeave.getAbsenceTime());

        }
    }
    /*public FinalModel(List<BiometricDetails> biometricDetails, List<HrnetDetails> hrnetDetails) {
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
