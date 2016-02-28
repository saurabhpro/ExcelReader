package combinedModel;

import jxcel.BiometricFileWorker;
import jxcel.HrnetFileWorker;
import jxcel.model.EmpBiometricDetails;
import jxcel.model.HolidaysList;
import jxcel.model.HrnetDetails;
import jxcel.model.LeaveType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static jxcel.model.AttendanceStatusType.*;

/**
 * Created by kumars on 2/16/2016.
 */
public class Combined2 {

    public static Map<String, FinalModel> newEmpMap = new TreeMap<>();
    private final Map<String, ArrayList<HrnetDetails>> hrnetDetails = HrnetFileWorker.hrnetDetails;
    private final Map<String, EmpBiometricDetails> empBiometricDetails = BiometricFileWorker.empList;

    public void combineFiles() {

        //Set the number of leaves applied
        for (String bd : empBiometricDetails.keySet()) {
            //setting the number of leaves after counting the list
            hrnetDetails.keySet().stream().filter(hr -> hr.equals(bd)).forEach
                    (hr -> empBiometricDetails.get(bd).setNumberOfLeaves(hrnetDetails.get(hr).size()));

            /*
        //Set the number of leaves applied
            for (String bd : empBiometricDetails.keySet()) {
                for (String hr : hrnetDetails.keySet()) {
                    if (hr.equals(bd)) {
                        System.out.println(hrnetDetails.get(hr).size() + " Size of ArrayList For " + empBiometricDetails.get(bd).name);

                         // setting the number of leaves after counting the list

                empBiometricDetails.get(bd).setNumberOfLeaves(hrnetDetails.get(hr).size());
                    }
                }
            }
             */
        }

        //this worked just fine for January
        for (EmpBiometricDetails bd : empBiometricDetails.values()) {
            for (HolidaysList h : HolidaysList.values()) {
                if (h.getDate().getMonth() == BiometricFileWorker.month) {
                    bd.attendanceOfDate[h.getDate().getDayOfMonth() - 1].setAttendanceStatusType(PUBLIC_HOLIDAY);
                }
            }

            for (int i = 0; i < 31; i++) {
                switch (bd.attendanceOfDate[i].getAttendanceStatusType()) {
                    case ABSENT:
                        //check for Work from home and half day
                        Set<Map.Entry<String, ArrayList<HrnetDetails>>> s = hrnetDetails.entrySet();
                        for (Map.Entry<String, ArrayList<HrnetDetails>> entry : s) {

                            if (bd.empId.equals(entry.getKey())) {

                                ArrayList<HrnetDetails> ar = entry.getValue();

                                for (HrnetDetails hr : ar) {
                                    LocalDate startDate = hr.attendanceOfLeave.getStartDate();
                                    // LocalDate endDate = hr.leaveDetails.getEndDate();
                                    double leaveTime = hr.attendanceOfLeave.getAbsenceTime();

                                    int changeDatesRange = startDate.getDayOfMonth() - 1;

                                    while (leaveTime > 0) {
                                        if (leaveTime == 0.5)
                                            bd.attendanceOfDate[changeDatesRange].setAttendanceStatusType(HALF_DAY);
                                        else if (hr.attendanceOfLeave.getLeaveType() == LeaveType.WORK_FROM_HOME) {
                                            bd.attendanceOfDate[changeDatesRange].setWorkTimeForDay(LocalTime.of((int) leaveTime * 8, 0));
                                            bd.attendanceOfDate[changeDatesRange].setAttendanceStatusType(PRESENT);
                                        }
                                        changeDatesRange++;
                                        leaveTime--;
                                    }
                                }

                            }
                        }
                }
            }
        }
        //Combine Hrnet and Biometric Files
        BiometricFileWorker.empList = empBiometricDetails;
        Iterator<EmpBiometricDetails> biometricDetailsIterator = empBiometricDetails.values().iterator();

        while (biometricDetailsIterator.hasNext()) {
            EmpBiometricDetails bd = biometricDetailsIterator.next();
            if (bd.getNumberOfLeaves() == 0) {
                newEmpMap.put(bd.empId, new FinalModel(bd.empId, bd.name, bd.numberOfLeaves, bd.attendanceOfDate, null));
            } else {
                Set<String> keySet = hrnetDetails.keySet();
                for (String s : keySet) {
                    if (s.equals(bd.empId)) {
                        ArrayList<HrnetDetails> hrnet;
                        hrnet = hrnetDetails.get(s);
                        newEmpMap.put(bd.empId, new FinalModel(bd.empId, bd.name, bd.numberOfLeaves, bd.attendanceOfDate, hrnet));
                    }
                }
            }
        }

        Iterator<FinalModel> iterator = newEmpMap.values().iterator();
        while (iterator.hasNext()) {
            FinalModel emp = iterator.next();
            for (int j = 0; j < 31; j++) {

                //AMRITA
                if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(ABSENT))
                    emp.setCount(0);
                else if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(PRESENT))
                    emp.setCount(1);
                else if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(PUBLIC_HOLIDAY))
                    emp.setCount(2);
                else if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(WEEKEND_HOLIDAY))
                    emp.setCount(3);
                else if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(HALF_DAY))
                    emp.setCount(4);
            }

        }

    }

    public void displayCombineFiles() {

        for (FinalModel emp : newEmpMap.values()) {
            System.out.println("Name: " + emp.name);
            System.out.println("Employee ID: " + emp.employeeID);
            System.out.println();

            System.out.println("Number Of Leaves Applied: " + emp.numberOfLeaves);
            if (emp.hrnetDetails != null) {
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
