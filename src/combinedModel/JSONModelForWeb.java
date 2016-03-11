package combinedModel;

import model.attendence.AttendanceOfDate;
import model.attendence.AttendanceStatusType;
import model.attendence.LeaveType;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by kumars on 3/11/2016.
 */
public class JSONModelForWeb {
    String empRevalId;
    String empSalesforceId;
    String empName;
    String empEmailId;
    String empAvgCheckInTimeForMonth;
    String empAvgCheckOutTimeForMonth;
    String empAvgWorkHoursForMonth;
    AttendanceOfDate[] attendanceOfDates;
    ArrayList<SubMenu> allDateDetailsList;
    Boolean empIfClarificationNeeded;


    public JSONModelForWeb(FinalModel f) {
        setEmpRevalId(f.getEmpId());
        setEmpSalesforceId(f.getSalesForceId());
        setEmpName(f.getName());
        setEmpEmailId(f.getEmailId());

        setEmpAvgCheckInTimeForMonth(f.getAvgInTime());
        setEmpAvgCheckOutTimeForMonth(f.getAvgOutTime());
        setEmpAvgWorkHoursForMonth(f.getAverageNumberOfHoursMonthly());

        setAttendanceOfDates(f.attendanceOfDate);
        setAllDateDetailsList();

        setEmpIfClarificationNeeded(f.getIfClarificationNeeded());
    }

    public JSONModelForWeb(FinalModel f, String discrepancy) {
        setEmpRevalId(f.getEmpId());
        setEmpSalesforceId(f.getSalesForceId());
        setEmpName(f.getName());
        setEmpEmailId(f.getEmailId());

        setEmpAvgCheckInTimeForMonth(f.getAvgInTime());
        setEmpAvgCheckOutTimeForMonth(f.getAvgOutTime());
        setEmpAvgWorkHoursForMonth(f.getAverageNumberOfHoursMonthly());

        setAttendanceOfDates(f.attendanceOfDate);
        setAllDateDetailsList(discrepancy);

        setEmpIfClarificationNeeded(f.getIfClarificationNeeded());
    }

    public void setAttendanceOfDates(AttendanceOfDate[] attendanceOfDates) {
        this.attendanceOfDates = attendanceOfDates;
    }

    public void setAllDateDetailsList() {
        allDateDetailsList = new ArrayList<>();
        for (AttendanceOfDate attendanceOfDate : attendanceOfDates) {
            this.allDateDetailsList.add(new SubMenu(attendanceOfDate));
        }
    }

    public String getEmpRevalId() {
        return empRevalId;
    }

    public void setEmpRevalId(String empRevalId) {
        this.empRevalId = empRevalId;
    }

    public String getEmpSalesforceId() {
        return empSalesforceId;
    }

    public void setEmpSalesforceId(String empSalesforceId) {
        this.empSalesforceId = empSalesforceId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpEmailId() {
        return empEmailId;
    }

    public void setEmpEmailId(String empEmailId) {
        this.empEmailId = empEmailId;
    }

    public String getEmpAvgCheckInTimeForMonth() {
        return empAvgCheckInTimeForMonth;
    }

    public void setEmpAvgCheckInTimeForMonth(LocalTime a) {
        this.empAvgCheckInTimeForMonth = a.toString();
    }

    public String getEmpAvgCheckOutTimeForMonth() {
        return empAvgCheckOutTimeForMonth;
    }

    public void setEmpAvgCheckOutTimeForMonth(LocalTime a) {
        this.empAvgCheckOutTimeForMonth = a.toString();
    }

    public String getEmpAvgWorkHoursForMonth() {
        return empAvgWorkHoursForMonth;
    }

    public void setEmpAvgWorkHoursForMonth(LocalTime a) {
        this.empAvgWorkHoursForMonth = a.toString();
    }

    public ArrayList<SubMenu> getAllDateDetailsList() {
        return allDateDetailsList;
    }

    public void setAllDateDetailsList(String Type) {
        allDateDetailsList = new ArrayList<>();
        for (AttendanceOfDate attendanceOfDate : attendanceOfDates) {
            if (attendanceOfDate.getAttendanceStatusType().equals(AttendanceStatusType.UNACCOUNTED_ABSENCE))
                this.allDateDetailsList.add(new SubMenu(attendanceOfDate));
        }
    }

    public Boolean getEmpIfClarificationNeeded() {
        return empIfClarificationNeeded;
    }

    public void setEmpIfClarificationNeeded(Boolean empIfClarificationNeeded) {
        this.empIfClarificationNeeded = empIfClarificationNeeded;
    }

    public void display() {
        System.out.println("EmpId" + getEmpRevalId());
        System.out.println("EmpSalesforce" + getEmpSalesforceId());
        System.out.println("Emp Name" + getEmpName());
        System.out.println("Emp Email" + getEmpEmailId());
        System.out.println("Emp Average check in" + getEmpAvgCheckInTimeForMonth());
        System.out.println("Emp Average check out" + getEmpAvgCheckOutTimeForMonth());
        System.out.println("Emp Average work hours" + getEmpAvgWorkHoursForMonth());
        System.out.println("Emp Clarification needed" + getEmpIfClarificationNeeded());

        for (SubMenu s : getAllDateDetailsList()) {
            System.out.println("Current date: " + s.currentDate);
            System.out.println("Check in: " + s.checkIn);
            System.out.println("Check out: " + s.checkOut);
            System.out.println("Work Time: " + s.workTimeForDay);
            System.out.println("Attendance Status: " + s.attendanceStatusType);
            System.out.println("Leave Type: " + s.leaveTypeForThisDate);
        }
    }

    class SubMenu {
        String currentDate;
        String checkIn;
        String checkOut;
        String workTimeForDay;
        AttendanceStatusType attendanceStatusType;
        LeaveType leaveTypeForThisDate;

        public SubMenu(AttendanceOfDate attendance) {
            currentDate = attendance.getCurrentDate().toString();
            if (attendance.getCheckIn() != null) checkIn = attendance.getCheckIn().toString();
            if (attendance.getCheckOut() != null) checkOut = attendance.getCheckOut().toString();
            if (attendance.getWorkTimeForDay() != null) workTimeForDay = attendance.getWorkTimeForDay().toString();
            attendanceStatusType = attendance.getAttendanceStatusType();
            leaveTypeForThisDate = attendance.getLeaveTypeForThisDate();
        }

        public String getCurrentDate() {
            return currentDate;
        }

        public String getCheckIn() {
            return checkIn;
        }

        public String getCheckOut() {
            return checkOut;
        }

        public String getWorkTimeForDay() {
            return workTimeForDay;
        }

        public AttendanceStatusType getAttendanceStatusType() {
            return attendanceStatusType;
        }

        public LeaveType getLeaveTypeForThisDate() {
            return leaveTypeForThisDate;
        }
    }

}

