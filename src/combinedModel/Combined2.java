package combinedModel;

import jxcel.BiometricFileWorker;
import jxcel.HrnetFileWorker;
import jxcel.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by kumars on 2/16/2016.
 */
public class Combined2 {

    public static List<FinalModel> newEmpList = new ArrayList<>();
    List<HrnetDetails> newHrnetDetails;

    Map<String, HrnetDetails> hrnetDetails = HrnetFileWorker.hrnetDetails;
    Map<String, EmpBiometricDetails> empBiometricDetails = BiometricFileWorker.empList;

    public void combineFiles() {

        //Set the number of leaves applied
        for (EmpBiometricDetails bd : empBiometricDetails.values()) {
            for (HrnetDetails hr : hrnetDetails.values()) {
                if (hr.employeeID.equals(bd.empId)) {
                    bd.setNumberOfLeaves();
                }
            }
        }

        for (EmpBiometricDetails bd : empBiometricDetails.values()) {
            for (HolidaysList h : HolidaysList.values()) {
                if (h.getDate().getMonth() == BiometricFileWorker.month) {
                    bd.attendanceOfDate[h.getDate().getDayOfMonth() - 1].setAttendanceStatusType(AttendanceStatusType.PUBLIC_HOLIDAY);
                }
            }

            for (int i = 0; i < 31; i++) {
                switch (bd.attendanceOfDate[i].getAttendanceStatusType()) {
                    case ABSENT:
                        //check for Work from home and half day
                        for (HrnetDetails hr : hrnetDetails.values()) {

                            if (hr.employeeID.equals(bd.empId)) {
                                LocalDate startDate = hr.attendanceOfLeave.getStartDate();
                                // LocalDate endDate = hr.leaveDetails.getEndDate();
                                double leaveTime = hr.attendanceOfLeave.getAbsenceTime();

                                int changeDatesRange = startDate.getDayOfMonth() - 1;

                                while (leaveTime > 0) {
                                    if (leaveTime == 0.5)
                                        bd.attendanceOfDate[changeDatesRange].setAttendanceStatusType(AttendanceStatusType.HALF_DAY);
                                    else if (hr.attendanceOfLeave.getLeaveTypes() == LeaveTypes.WORK_FROM_HOME) {
                                        bd.attendanceOfDate[changeDatesRange].setWorkTimeForDay(LocalTime.of((int) leaveTime * 8, 0));
                                        bd.attendanceOfDate[changeDatesRange].setAttendanceStatusType(AttendanceStatusType.PRESENT);
                                    }
                                    changeDatesRange++;
                                    leaveTime--;
                                }
                            }

                        }
                }
            }


        }

        //Combine Hrnet and Biometric Files
        BiometricFileWorker.empList = empBiometricDetails;
        Iterator<EmpBiometricDetails> biometricDetailsIterator = empBiometricDetails.values().iterator();
        Iterator<HrnetDetails> hrnetDetailsIterator;

        while (biometricDetailsIterator.hasNext()) {
            EmpBiometricDetails bd = biometricDetailsIterator.next();
            if (bd.getNumberOfLeaves() == 0) {
                newEmpList.add(new FinalModel(bd.empId, bd.name, bd.numberOfLeaves, bd.attendanceOfDate, null));
            } else {
                hrnetDetailsIterator = hrnetDetails.values().iterator();
                newHrnetDetails = new ArrayList<>();
                while (hrnetDetailsIterator.hasNext()) {
                    HrnetDetails hr = hrnetDetailsIterator.next();

                    if (hr.employeeID.equals(bd.empId)) {
                        newHrnetDetails.add(new HrnetDetails(hr.employeeID, hr.name, hr.requestID, hr.attendanceOfLeave));
                    }
                }
                newEmpList.add(new FinalModel(bd.empId, bd.name, bd.numberOfLeaves, bd.attendanceOfDate, newHrnetDetails));
            }
        }

        Iterator<FinalModel> iterator = newEmpList.iterator();
        while (iterator.hasNext()) {
            FinalModel emp = iterator.next();
            for (int j = 0; j < 31; j++) {

                //AMRITA
                if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(AttendanceStatusType.ABSENT))
                    emp.setCount(0);
                else if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(AttendanceStatusType.PRESENT))
                    emp.setCount(1);
                else if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(AttendanceStatusType.PUBLIC_HOLIDAY))
                    emp.setCount(2);
                else if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(AttendanceStatusType.WEEKEND_HOLIDAY))
                    emp.setCount(3);
                else if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(AttendanceStatusType.HALF_DAY))
                    emp.setCount(4);
            }

        }
    }

    //Display the two combined files
    public void displayCombineFiles() {

        Iterator<FinalModel> iterator = newEmpList.iterator();
        while (iterator.hasNext()) {
            FinalModel emp = iterator.next();

            System.out.println("Name: " + emp.name);
            System.out.println("Employee ID: " + emp.employeeID);
            System.out.println();

            System.out.println("Number Of Leaves Applied: " + emp.numberOfLeaves);
            if (emp.empList1 != null) {
                emp.displayArrayList();
            }


            //AMRITA
            System.out.println("\nNumber of Absent Days " + emp.getCount(0));
            System.out.println("Number of Present Days " + emp.getCount(1));
            System.out.println("Number of Public Holidays " + emp.getCount(2));
            System.out.println("Number of Weekend Holidays " + emp.getCount(3));
            System.out.println("Number of Half Days " + emp.getCount(4));

            System.out.println("\nBiometric Data for Each Day: ");
            for (int j = 0; j < 31; j++) {
                System.out.print(emp.attendanceOfDate[j].getCurrentDate());
                System.out.print("\tIn Time: " + emp.attendanceOfDate[j].getCheckIn());
                System.out.print("\tOut Time: " + emp.attendanceOfDate[j].getCheckOut());
                System.out.print("\tStatus: " + emp.attendanceOfDate[j].getAttendanceStatusType() + "\n");

            }

            System.out.println();
        }
    }
}
