package combinedModel;

import jxcel.HrnetFileWorker;
import jxcel.JxcelBiometricFileWorker;
import jxcel.model.EmpBiometricDetails;
import jxcel.model.HolidaysList;
import jxcel.model.HrnetDetails;
import jxcel.model.LeaveType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static jxcel.model.AttendanceStatusType.*;

/**
 * Created by kumars on 2/16/2016.
 */
public class Combined2 {

    //Comparator needs string as Type
    public static Map<String, FinalModel> newEmpMap = new TreeMap<>(String::compareTo);
    private final Map<String, ArrayList<HrnetDetails>> hrnetDetails = HrnetFileWorker.hrnetDetails;
    private final Map<String, EmpBiometricDetails> empBiometricDetails = JxcelBiometricFileWorker.empList;

    public void combineFiles() {

        //Set the number of leaves applied
        for (String bd : empBiometricDetails.keySet()) {
            //setting the number of leaves after counting the list
            hrnetDetails.keySet().stream().filter(hr -> hr.equals(bd)).forEach
                    (hr -> empBiometricDetails.get(bd).setNumberOfLeaves(hrnetDetails.get(hr).size()));
        }

        //this worked just fine for January
        for (EmpBiometricDetails bd : empBiometricDetails.values()) {
            for (HolidaysList h : HolidaysList.values()) {
                if (h.getDate().getMonth() == JxcelBiometricFileWorker.month) {
                    bd.attendanceOfDate[h.getDate().getDayOfMonth() - 1].setAttendanceStatusType(PUBLIC_HOLIDAY);
                }
            }

            for (int i = 0; i < JxcelBiometricFileWorker.month.maxLength(); i++) {
                switch (bd.attendanceOfDate[i].getAttendanceStatusType()) {
                    case ABSENT:
                        //check for Work from home and half day
                        Set<Map.Entry<String, ArrayList<HrnetDetails>>> s = hrnetDetails.entrySet();
                        for (Map.Entry<String, ArrayList<HrnetDetails>> entry : s) {

                            if (bd.getEmpId().equals(entry.getKey())) {

                                ArrayList<HrnetDetails> ar = entry.getValue();

                                for (HrnetDetails hr : ar) {
                                    LocalDate startDate = hr.attendanceOfLeave.getStartDate();
                                    // LocalDate endDate = hr.leaveDetails.getEndDate();
                                    double leaveTime = hr.attendanceOfLeave.getAbsenceTime();

                                    int changeDatesRange = startDate.getDayOfMonth() - 1;

                                    while (leaveTime > 0) {
                                        if (leaveTime == 0.5)
                                            bd.attendanceOfDate[changeDatesRange].setAttendanceStatusType(HALF_DAY);
                                        else if (hr.attendanceOfLeave.getLeaveType() == LeaveType.WORK_FROM_HOME_IND) {
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
        JxcelBiometricFileWorker.empList = empBiometricDetails;

        for (EmpBiometricDetails bd : empBiometricDetails.values()) {
            if (bd.getNumberOfLeaves() == 0) {
                newEmpMap.put(bd.getName(), new FinalModel(bd.getEmpId(), bd.getName(), bd.numberOfLeaves, bd.attendanceOfDate, null));
            } else {
                Set<String> keySet = hrnetDetails.keySet();
                for (String s : keySet) {
                    if (s.equals(bd.getEmpId())) {
                        ArrayList<HrnetDetails> hrnet;
                        hrnet = hrnetDetails.get(s);
                        newEmpMap.put(bd.getName(), new FinalModel(bd.getEmpId(), bd.getName(), bd.numberOfLeaves, bd.attendanceOfDate, hrnet));
                    }
                }
            }
        }

        //to be removed  today
        for (FinalModel emp : newEmpMap.values()) {
            for (int j = 0; j < JxcelBiometricFileWorker.month.maxLength(); j++) {

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
        System.out.println(JxcelBiometricFileWorker.month);
        newEmpMap.values().forEach(FinalModel::displayFinalList);
    }
}
