package combinedModel;

import DataUsage.ReadAndKeepData;
import jxcel.HrnetFileWorker;
import jxcel.JxcelBiometricFileWorker;
import jxcel.model.*;

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
        String salesForce = null;
        //Set the number of leaves applied
        for (String bEmpID : empBiometricDetails.keySet()) {
            if (ReadAndKeepData.employeeDetailsMap.containsKey(bEmpID)) {
                salesForce = ReadAndKeepData.employeeDetailsMap.get(bEmpID).getSalesForceId();
            }

            //setting the number of leaves after counting the list
            final String finalSalesForce = salesForce;
            hrnetDetails.keySet().stream().filter(hr -> hr.equals(finalSalesForce)).forEach
                    (hr -> empBiometricDetails.get(bEmpID).setNumberOfLeaves(hrnetDetails.get(hr).size()));
        }

        //this worked just fine for January
        for (EmpBiometricDetails empObj : empBiometricDetails.values()) {
            for (HolidaysList h : HolidaysList.values()) {
                if (h.getDate().getMonth() == JxcelBiometricFileWorker.month) {
                    empObj.attendanceOfDate[h.getDate().getDayOfMonth() - 1].setAttendanceStatusType(PUBLIC_HOLIDAY);
                }
            }

            for (int i = 0; i < JxcelBiometricFileWorker.month.maxLength(); i++) {
                switch (empObj.attendanceOfDate[i].getAttendanceStatusType()) {
                    case ABSENT:
                        //check for Work from home and half day
                        Set<Map.Entry<String, ArrayList<HrnetDetails>>> hrDataSet = hrnetDetails.entrySet();
                        for (Map.Entry<String, ArrayList<HrnetDetails>> hrEntry : hrDataSet) {
                            BasicEmployeeDetails tempSalesForceId = ReadAndKeepData.employeeDetailsMap.get(empObj.getEmpId());

                            if (tempSalesForceId != null && tempSalesForceId.getSalesForceId() != null)
                                if (tempSalesForceId.getSalesForceId().equals(hrEntry.getKey())) {

                                    ArrayList<HrnetDetails> ar = hrEntry.getValue();

                                    for (HrnetDetails hr : ar) {
                                        LocalDate startDate = hr.attendanceOfLeave.getStartDate();
                                        // LocalDate endDate = hr.leaveDetails.getEndDate();
                                        double leaveTime = hr.attendanceOfLeave.getAbsenceTime();

                                        int changeDatesRange = startDate.getDayOfMonth() - 1;


                                        while (leaveTime > 0) {


                                            if (leaveTime == 0.5) {
                                                empObj.attendanceOfDate[changeDatesRange].setAttendanceStatusType(HALF_DAY);

                                            } else if (hr.attendanceOfLeave.getLeaveType() == LeaveType.WORK_FROM_HOME_IND) {
                                                empObj.attendanceOfDate[changeDatesRange].setWorkTimeForDay(LocalTime.of(8, 0));
                                                empObj.attendanceOfDate[changeDatesRange].setAttendanceStatusType(PRESENT);
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
        for (EmpBiometricDetails empObj : empBiometricDetails.values()) {
            for (int i = 0; i < JxcelBiometricFileWorker.month.maxLength(); i++) {
                if (empObj.attendanceOfDate[i].getAttendanceStatusType().equals(HALF_DAY)) {
                    LocalTime time = empObj.attendanceOfDate[i].getWorkTimeForDay();
                    if (time == null)
                        empObj.attendanceOfDate[i].setWorkTimeForDay(LocalTime.of(4, 0));
                    else
                        empObj.attendanceOfDate[i].setWorkTimeForDay(time.plusHours(4));
                }
            }
        }

        //Combine Hrnet and Biometric Files
        JxcelBiometricFileWorker.empList = empBiometricDetails;

        for (EmpBiometricDetails empObj : empBiometricDetails.values()) {
            if (empObj.getNumberOfLeaves() == 0) {
                newEmpMap.put(empObj.getName(), new FinalModel(empObj.getEmpId(), empObj.getName(), empObj.numberOfLeaves, empObj.attendanceOfDate, null));
            } else {
                Set<String> hrKeySet = hrnetDetails.keySet();
                for (String hrKey : hrKeySet) {
                    BasicEmployeeDetails tempSalesForceId = ReadAndKeepData.employeeDetailsMap.get(empObj.getEmpId());
                    if (tempSalesForceId != null && tempSalesForceId.getSalesForceId() != null)
                        if (hrKey.equals(tempSalesForceId.getSalesForceId())) {
                            ArrayList<HrnetDetails> hrnet;
                            hrnet = hrnetDetails.get(hrKey);
                            newEmpMap.put(empObj.getName(), new FinalModel(empObj.getEmpId(), empObj.getName(), empObj.numberOfLeaves, empObj.attendanceOfDate, hrnet));
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
