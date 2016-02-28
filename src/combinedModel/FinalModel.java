package combinedModel;

import jxcel.attendence.AttendanceOfDate;
import jxcel.model.HrnetDetails;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    public ArrayList<HrnetDetails> hrnetDetails;

    public FinalModel(String employeeID, String name, int numberOfLeaves, AttendanceOfDate[] attendanceOfDate, ArrayList<HrnetDetails> hrnetDetails) {
        this.employeeID = employeeID;
        this.name = name;
        this.attendanceOfDate = attendanceOfDate;
        this.numberOfLeaves = numberOfLeaves;
        //this.needClarificationFromEmployee = needClarificationFromEmployee;
        for (int i = 0; i < 5; i++) {
            count[i] = 0;
        }
        this.hrnetDetails = hrnetDetails;
    }

    public int getCount(int i) {
        return count[i];
    }

    public void setCount(int i) {
        count[i] = count[i] + 1;
    }

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
