package combinedModel;

import jxcel.JxcelBiometricFileWorker;
import jxcel.attendence.AttendanceOfDate;
import jxcel.model.BasicEmployeeDetails;
import jxcel.model.HrnetDetails;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.ArrayList;

import static jxcel.TimeManager.calculate;

/**
 * Created by kumars on 2/19/2016.
 */
public class FinalModel extends BasicEmployeeDetails {
    public final AttendanceOfDate[] attendanceOfDate;
    private final LocalTime avgInTime;
    private final LocalTime avgOutTime;
    private final int numberOfLeaves;
    public boolean setIfClarificationFromEmployee;
    public ArrayList<HrnetDetails> hrnetDetails;
    //AMRITA
    private int[] count = new int[5];//Absent, Present, Public_Holiday, Weekend_Holiday, Half_Day

    public FinalModel(String EmployeeID, String name, int numberOfLeaves, AttendanceOfDate[] a, ArrayList<HrnetDetails> hr1) {
        this.setEmpId(EmployeeID);
        this.setName(name);
        this.attendanceOfDate = a;
        this.numberOfLeaves = numberOfLeaves;
        //this.needClarificationFromEmployee = needClarificationFromEmployee;
        this.hrnetDetails = hr1;

        avgInTime = setAvgInTime();
        avgOutTime = setAvgOutTime();

    }

    @NotNull
    public LocalTime getAvgInTime() {
        return avgInTime;
    }

    @NotNull
    private LocalTime setAvgInTime() {
        return calculate("AverageCheckInTime", attendanceOfDate);
    }

    public LocalTime getAvgOutTime() {
        return avgOutTime;
    }

    @NotNull
    private LocalTime setAvgOutTime() {
        return calculate("AverageCheckOutTime", attendanceOfDate);
    }

    //only for debugging
    public int getCount(int i) {
        return count[i];
    }

    public void setCount(int i) {
        count[i] = count[i] + 1;
    }

    public void displayArrayList() {
        hrnetDetails.forEach(HrnetDetails::printHrNetDetail);
    }

    public void setIfClarificationFromEmployee(boolean needClarificationFromEmployee) {
        this.setIfClarificationFromEmployee = needClarificationFromEmployee;
    }

    public void displayFinalList() {
        System.out.println("Name: " + this.getName());
        System.out.println("Employee ID: " + this.getEmpId());
        System.out.println("Avg In Time " + this.getAvgInTime());
        System.out.println("Avg Out Time " + this.getAvgOutTime());
        System.out.println();

        System.out.println("Number Of Leaves Applied: " + this.numberOfLeaves);
        if (this.hrnetDetails != null) {
            this.displayArrayList();
        }


        //to be removed  today
        //AMRITA
        System.out.println("\nNumber of Absent Days " + this.getCount(0));
        System.out.println("Number of Present Days " + this.getCount(1));
        System.out.println("Number of Public Holidays " + this.getCount(2));
        System.out.println("Number of Weekend Holidays " + this.getCount(3));
        System.out.println("Number of Half Days " + this.getCount(4));

        System.out.println("\nBiometric Data for Each Day: ");
        for (int j = 0; j < JxcelBiometricFileWorker.month.maxLength(); j++) {
            System.out.print(this.attendanceOfDate[j].getCurrentDate());
            System.out.print("\tIn Time: " + this.attendanceOfDate[j].getCheckIn());
            System.out.print("\tOut Time: " + this.attendanceOfDate[j].getCheckOut());
            System.out.print("\tStatus: " + this.attendanceOfDate[j].getAttendanceStatusType() + "\n");
            System.out.print("\tWorkhours: " + this.attendanceOfDate[j].getWorkTimeForDay() + "\n");

        }

        System.out.println();
    }

}
