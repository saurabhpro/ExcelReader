package jxcel.model;

import jxcel.attendence.AttendanceOfLeave;

/**
 * Created by AroraA on 09-02-2016.
 */
public class HrnetDetails extends BasicEmployeeDetails {
    public final String requestID;
    public final AttendanceOfLeave attendanceOfLeave;


    public HrnetDetails(String empId, String name, String requestID, AttendanceOfLeave attendanceOfLeave) {
        setEmpId(empId);
        setName(name);
        this.requestID = requestID;
        this.attendanceOfLeave = attendanceOfLeave;
    }

    public void printHrNetDetail() {
        System.out.print(this.getEmpId());
        System.out.print("\t" + this.getName());
        System.out.print("\t" + this.requestID);
        System.out.print("\t" + this.attendanceOfLeave.getLeaveType());
        System.out.print("\t" + this.attendanceOfLeave.getStartDate() + "\t" + this.attendanceOfLeave.getEndDate());
        System.out.println("\t" + this.attendanceOfLeave.getAbsenceTime());
    }
}
