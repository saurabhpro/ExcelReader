package combinedModel;

import emplmasterrecord.EmployeeMasterData;
import jxcel.BiometricFileWorker;
import jxcel.HrnetFileWorker;
import jxcel.TimeManager;
import model.BasicEmployeeDetails;
import model.EmpBiometricDetails;
import model.HrnetDetails;
import model.attendence.HolidaysList;
import model.attendence.LeaveType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static model.attendence.AttendanceStatusType.*;

/**
 * Created by kumars on 2/16/2016.
 * * 6-03-2016 changed the Type from ABSENT to UNACCOUNTED_ABSENCE.
 */
public class Combined2 {

    //Comparator needs string as Type
    public static Map<String, FinalModel> EmpCombinedMap;
    private final Map<String, ArrayList<HrnetDetails>> empHrnetDetails;
    private final Map<String, EmpBiometricDetails> empBiometricDetails;

    public Combined2() {
        EmpCombinedMap = new TreeMap<>(String::compareTo);
        empHrnetDetails = HrnetFileWorker.hrnetDetails;
        empBiometricDetails = BiometricFileWorker.empList;
    }

    @SuppressWarnings({"incomplete-switch"})
    public void combineFiles() {
        String salesForce = null;
        //Set the number of leaves applied
        for (String bEmpID : empBiometricDetails.keySet()) {
            if (EmployeeMasterData.allEmployeeRecordMap.containsKey(bEmpID)) {
                salesForce = new BasicEmployeeDetails().getSalesForceId(bEmpID);
            }

            //setting the number of leaves after counting the list
            final String finalSalesForce = salesForce;
            empHrnetDetails.keySet().stream().filter(hr -> hr.equals(finalSalesForce)).forEach
                    (hr -> empBiometricDetails.get(bEmpID).setNumberOfLeaves(empHrnetDetails.get(hr).size()));
        }

        //set public holiday status
        for (EmpBiometricDetails empObj : empBiometricDetails.values()) {
            for (HolidaysList h : HolidaysList.values()) {
                if (h.getDate().getMonth() == TimeManager.getMonth()) {
                    empObj.attendanceOfDate[h.getDate().getDayOfMonth() - 1].setAttendanceStatusType(PUBLIC_HOLIDAY);
                }
            }
        }
        //this worked just fine for January
        for (EmpBiometricDetails empObj : empBiometricDetails.values()) {

            for (int i = 0; i < TimeManager.getMonth().maxLength(); i++) {
                switch (empObj.attendanceOfDate[i].getAttendanceStatusType()) {
                    case ABSENT:
                        //check for Work from home and half day
                        Set<Map.Entry<String, ArrayList<HrnetDetails>>> hrDataSet = empHrnetDetails.entrySet();

                        for (Map.Entry<String, ArrayList<HrnetDetails>> hrEntry : hrDataSet) {
                            String tempSalesForceId = new BasicEmployeeDetails().getSalesForceId(empObj.getEmpId());

                            if (tempSalesForceId != null && tempSalesForceId.equals(hrEntry.getKey())) {

                                for (HrnetDetails hr : hrEntry.getValue()) {
                                    double leaveTime = hr.attendanceOfLeave.getAbsenceTime();
                                    LocalDate tempStart = hr.attendanceOfLeave.getStartDate();
                                    LocalDate tempEnd = hr.attendanceOfLeave.getEndDate();
                                    int changeDatesRange;
                                    do {
                                        changeDatesRange = tempStart.getDayOfMonth() - 1;

                                        if (leaveTime == 0.5) {
                                            empObj.attendanceOfDate[changeDatesRange].setAttendanceStatusType(HALF_DAY);

                                        } else if (hr.attendanceOfLeave.getLeaveType() == LeaveType.WORK_FROM_HOME_IND) {
                                            empObj.attendanceOfDate[changeDatesRange].setWorkTimeForDay(LocalTime.of(6, 0));
                                            //set work from home as 6 hours
                                            empObj.attendanceOfDate[changeDatesRange].setAttendanceStatusType(PRESENT);
                                        }
                                        leaveTime--;
                                        tempStart = tempStart.plusDays(1);

                                        if (tempStart.equals(tempEnd)) break;
                                    }
                                    while (leaveTime > 0 && tempStart.getMonth().equals(TimeManager.getMonth()));
                                }
                            }
                        }
                        break;
                }
            }
        }

        //update number of work hours for half day
        for (EmpBiometricDetails empObj : empBiometricDetails.values()) {
            for (int i = 0; i < TimeManager.getMonth().maxLength(); i++) {
                //06-03-2016 changed the Type from ABSENT to UNACCOUNTED_ABSENCE.
                if (empObj.attendanceOfDate[i].getAttendanceStatusType().equals(ABSENT))
                    empObj.attendanceOfDate[i].setAttendanceStatusType(UNACCOUNTED_ABSENCE);

                else if (empObj.attendanceOfDate[i].getAttendanceStatusType().equals(HALF_DAY)) {
                    LocalTime time = empObj.attendanceOfDate[i].getWorkTimeForDay();
                    if (time == null)
                        empObj.attendanceOfDate[i].setWorkTimeForDay(LocalTime.of(4, 0));
                    else
                        empObj.attendanceOfDate[i].setWorkTimeForDay(time.plusHours(4));
                }
            }
        }

        //update the basic employee biometric file
        BiometricFileWorker.empList = empBiometricDetails;

        //Combine Hrnet and Biometric Files
        for (EmpBiometricDetails empObj : empBiometricDetails.values()) {
            if (empObj.getNumberOfLeaves() == 0) {
                EmpCombinedMap.put(empObj.getEmpId(), new FinalModel(empObj.getEmpId(),
                        empObj.numberOfLeaves, empObj.attendanceOfDate, null));
            } else {
                Set<String> hrKeySet = empHrnetDetails.keySet();
                for (String hrKey : hrKeySet) {
                    String tempSalesForceId = new BasicEmployeeDetails().getSalesForceId(empObj.getEmpId());
                    if (tempSalesForceId != null && hrKey.equals(tempSalesForceId)) {
                        ArrayList<HrnetDetails> hrnet = empHrnetDetails.get(hrKey);
                        EmpCombinedMap.put(empObj.getEmpId(), new FinalModel(empObj.getEmpId(),
                                empObj.numberOfLeaves, empObj.attendanceOfDate, hrnet));
                    }
                }
            }
        }

        //to be removed  today
        for (FinalModel emp : EmpCombinedMap.values()) {
            for (int j = 0; j < TimeManager.getMonth().maxLength(); j++) {

                //AMRITA
                if (emp.attendanceOfDate[j].getAttendanceStatusType().equals(UNACCOUNTED_ABSENCE))
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
        System.out.println(TimeManager.getMonth());
        EmpCombinedMap.values().forEach(FinalModel::displayFinalList);
    }
}
