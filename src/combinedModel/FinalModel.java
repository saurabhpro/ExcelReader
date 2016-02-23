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

    //AMRITA
    public int[] count = new int[5];//Absent, Present, Public_Holiday, Weekend_Holiday, Half_Day
    public List<HrnetDetails> empList1 = new ArrayList<>();

    public FinalModel(String employeeID, String name, int numberOfLeaves, AttendanceOfDate[] attendanceOfDate, List<HrnetDetails> empList1) {
        this.employeeID = employeeID;
        this.name = name;
        this.attendanceOfDate = attendanceOfDate;
        this.empList1 = empList1;
        this.numberOfLeaves = numberOfLeaves;
        //this.needClarificationFromEmployee = needClarificationFromEmployee;
        for (int i = 0; i < 5; i++) {
            count[i] = 0;
        }
    }

    public int getCount(int i) {
        return count[i];
    }

    public void setCount(int i) {
        count[i] = count[i] + 1;
    }

    public void displayArrayList() {
        for (HrnetDetails hr : empList1) {
            System.out.print(hr.employeeID);
            System.out.print("\t" + hr.name);
            System.out.print("\t" + hr.requestID);
            System.out.print("\t" + hr.attendanceOfLeave.getLeaveTypes());
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
