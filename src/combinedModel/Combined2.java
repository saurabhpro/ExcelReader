package combinedModel;

import com.sun.org.apache.xpath.internal.SourceTree;
import jxcel.BiometricFileWorker;
import jxcel.HrnetFileWorker;
import jxcel.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kumars on 2/16/2016.
 */
public class Combined2 {

    List<FinalModel> newEmpList = new ArrayList<>();
    List<HrnetDetails> newHrnetDetails;

    List<HrnetDetails> hrnetDetails = HrnetFileWorker.hrnetDetails;
    List<BiometricDetails> biometricDetails = BiometricFileWorker.empList;

    public void combineFiles() {

        //Set the number of leaves applied
        for (BiometricDetails bd : biometricDetails) {
            for (HrnetDetails hr : hrnetDetails) {
                if (hr.employeeID.equals(bd.empId)) {
                    bd.setNumberOfLeaves();
                }
            }
        }

        for (BiometricDetails bd : biometricDetails) {
            for (HolidaysList h : HolidaysList.values()) {
                if (h.getDate().getMonth() == BiometricFileWorker.month) {
                    bd.attendanceOfDate[h.getDate().getDayOfMonth() - 1].setBiometricAttendanceStatusTypes(BiometricAttendanceStatusTypes.PUBLIC_HOLIDAY);
                }
            }

            for (int i = 0; i < 31; i++) {
                switch (bd.attendanceOfDate[i].getBiometricAttendanceStatusTypes()) {
                    case ABSENT:
                        //check for Work from home and half day
                        for (HrnetDetails hr : hrnetDetails) {

                            if (hr.employeeID.equals(bd.empId)) {
                                LocalDate startDate = hr.attendanceOfLeave.getStartDate();
                                // LocalDate endDate = hr.leaveDetails.getEndDate();
                                double leaveTime = hr.attendanceOfLeave.getAbsenceTime();

                                int changeDatesRange = startDate.getDayOfMonth() - 1;

                                while (leaveTime > 0) {
                                    if (leaveTime == 0.5)
                                        bd.attendanceOfDate[changeDatesRange].setBiometricAttendanceStatusTypes(BiometricAttendanceStatusTypes.HALF_DAY);
                                    else if (hr.attendanceOfLeave.getLeaveTypes() == LeaveTypes.WORK_FROM_HOME) {
                                        bd.attendanceOfDate[changeDatesRange].setWorkTimeForDay(LocalTime.of((int) leaveTime * 8, 0));
                                        bd.attendanceOfDate[changeDatesRange].setBiometricAttendanceStatusTypes(BiometricAttendanceStatusTypes.PRESENT);
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
        BiometricFileWorker.empList = biometricDetails;
        Iterator<BiometricDetails> biometricDetailsIterator = biometricDetails.iterator();
        Iterator<HrnetDetails> hrnetDetailsIterator;

        while (biometricDetailsIterator.hasNext()) {
            BiometricDetails bd = biometricDetailsIterator.next();
            if (bd.getNumberOfLeaves() == 0) {
                newEmpList.add(new FinalModel(bd.empId, bd.name, bd.numberOfLeaves, bd.attendanceOfDate, bd.needClarificationFromEmployee, null));
            } else {
                hrnetDetailsIterator = hrnetDetails.iterator();
                newHrnetDetails = new ArrayList<>();
                while (hrnetDetailsIterator.hasNext()) {
                    HrnetDetails hr = hrnetDetailsIterator.next();

                    if (hr.employeeID.equals(bd.empId)) {
                        newHrnetDetails.add(new HrnetDetails(hr.employeeID, hr.name, hr.requestID, hr.attendanceOfLeave));
                    }
                }
                newEmpList.add(new FinalModel(bd.empId, bd.name, bd.numberOfLeaves, bd.attendanceOfDate, bd.needClarificationFromEmployee, newHrnetDetails));
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

            System.out.println("\nBiometric Data for Each Day: ");
            for (int j = 0; j < 31; j++) {
                System.out.print(emp.attendanceOfDate[j].getCurrentDate());
                System.out.print("\tIn Time: " + emp.attendanceOfDate[j].getCheckIn());
                System.out.print("\tOut Time: " + emp.attendanceOfDate[j].getCheckOut());
                System.out.print("\tStatus: " + emp.attendanceOfDate[j].getBiometricAttendanceStatusTypes() + "\n");
            }

            System.out.println();
        }
    }
}
