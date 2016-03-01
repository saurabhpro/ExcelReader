package combinedModel;

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
    private final LocalTime avgInTime;
    private final LocalTime avgOutTime;
    public AttendanceOfDate[] attendanceOfDate;
    public boolean needClarificationFromEmployee;
    public int numberOfLeaves;
    //AMRITA
    public int[] count = new int[5];//Absent, Present, Public_Holiday, Weekend_Holiday, Half_Day
    public ArrayList<HrnetDetails> hrnetDetails;

    public FinalModel(String employeeID, String name, int numberOfLeaves, AttendanceOfDate[] a, ArrayList<HrnetDetails> hr1) {
        this.setEmpId(employeeID);
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

    public LocalTime setAvgOutTime() {
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
        this.needClarificationFromEmployee = needClarificationFromEmployee;
    }
}
