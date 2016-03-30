package model;

import model.attendence.AttendanceOfDate;
import model.attendence.AttendanceStatusType;
import model.attendence.LeaveType;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by kumars on 3/11/2016.
 */
public class JSONModelForWeb {
    private String empRevalId;
    private String empSalesforceId;
    private String empName;
    private String empEmailId;
    private String empAvgCheckInTimeForMonth;
    private String empAvgCheckOutTimeForMonth;
    private String empAvgWorkHoursForMonth;
    private AttendanceOfDate[] attendanceOfDates;
    private ArrayList<SubMenuAttendanceOfDate> allDateDetailsList;
    private Boolean empIfClarificationNeeded;

    public JSONModelForWeb(FinalModel f) {
        setEmpRevalId(f.getEmpId());
        setEmpSalesforceId(f.getSalesForceId());
        setEmpName(f.getName());
        setEmpEmailId(f.getEmailId());

        setEmpAvgCheckInTimeForMonth(f.getAvgInTime());
        setEmpAvgCheckOutTimeForMonth(f.getAvgOutTime());
        setEmpAvgWorkHoursForMonth(f.getAverageNumberOfHoursMonthly());
        setEmpIfClarificationNeeded(f.getIfClarificationNeeded());

        setAttendanceOfDates(f.attendanceOfDate);
        setAllDateDetailsList();
    }

    public JSONModelForWeb(FinalModel f, String discrepancy) {
        this(f);
        setAllDateDetailsList(discrepancy);
    }

    public void setAttendanceOfDates(AttendanceOfDate[] attendanceOfDates) {
        this.attendanceOfDates = attendanceOfDates;
    }

    public void setAllDateDetailsList() {
        allDateDetailsList = new ArrayList<>();
        for (AttendanceOfDate attendanceOfDate : attendanceOfDates) {
            this.allDateDetailsList.add(new SubMenuAttendanceOfDate(attendanceOfDate));
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

    public ArrayList<SubMenuAttendanceOfDate> getAllDateDetailsList() {
        return allDateDetailsList;
    }

    public void setAllDateDetailsList(String Type) {
        allDateDetailsList = new ArrayList<>();
        for (AttendanceOfDate attendanceOfDate : attendanceOfDates) {
            if (attendanceOfDate.getAttendanceStatusType().equals(AttendanceStatusType.UNACCOUNTED_ABSENCE))
                this.allDateDetailsList.add(new SubMenuAttendanceOfDate(attendanceOfDate));
        }
    }

    public Boolean getEmpIfClarificationNeeded() {
        return empIfClarificationNeeded;
    }

    public void setEmpIfClarificationNeeded(Boolean empIfClarificationNeeded) {
        this.empIfClarificationNeeded = empIfClarificationNeeded;
    }

    private void displayBasicDetails() {
        System.out.println("\nEmpId " + getEmpRevalId());
        System.out.println("EmpSalesforce " + getEmpSalesforceId());
        System.out.println("Emp Name" + getEmpName());
        System.out.println("Emp Email" + getEmpEmailId());
        System.out.println("Emp Average check in " + getEmpAvgCheckInTimeForMonth());
        System.out.println("Emp Average check out " + getEmpAvgCheckOutTimeForMonth());
        System.out.println("Emp Average work hours " + getEmpAvgWorkHoursForMonth());
        System.out.println("Emp Clarification needed " + getEmpIfClarificationNeeded());

    }

    public void displayAllDates() {
        displayBasicDetails();
        getAllDateDetailsList().forEach(SubMenuAttendanceOfDate::displaySub);
    }

    public HolidayWorkerModel getHolidayWorkerObjForThisDate(int date) {
        SubMenuAttendanceOfDate s = allDateDetailsList.get(date);
        if (s.getCheckIn() != null)
            return new HolidayWorkerModel(new BasicEmployeeDetails(empName, empRevalId, empSalesforceId, empEmailId), s);

        return null;
    }

    public static class SubMenuAttendanceOfDate {
        private final String currentDate;
        private final AttendanceStatusType attendanceStatusType;
        private final LeaveType leaveTypeForThisDate;
        private String checkIn;
        private String checkOut;
        private String workTimeForDay;

        public SubMenuAttendanceOfDate(AttendanceOfDate attendance) {
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

        public void displaySub() {
            System.out.println("Date: " + getCurrentDate());
            System.out.println("Check in: " + getCheckIn());
            System.out.println("Check out: " + getCheckOut());
            System.out.println("Work Time: " + getWorkTimeForDay());
            System.out.println("Attendance Status: " + getAttendanceStatusType());
            System.out.println("Leave Type: " + getLeaveTypeForThisDate());
        }
    }
}