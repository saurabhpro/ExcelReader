package combinedModel;

import jxcel.JxcelBiometricFileWorker;
import jxcel.model.HrnetDetails;

import java.time.LocalDate;
import java.util.Map;

import static jxcel.model.AttendanceStatusType.*;
import static jxcel.model.LeaveType.WORK_FROM_HOME_IND;

/**
 * Created by AroraA on 17-02-2016.
 */

public class Discrepancy {

    public void findDiscrepancy() {
        Map<String, FinalModel> newEmpMap = Combined2.newEmpMap;
        for (FinalModel finalModel : newEmpMap.values()) {
            //Discrepancy if an employee is absent and there is no entry in Hrnet file.
            if (finalModel.hrnetDetails == null) {
                for (int j = 0; j < JxcelBiometricFileWorker.month.maxLength(); j++) {
                    if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(ABSENT)) {
                        System.out.println("Null List- Discrepancy Set for " + finalModel.getName() + " Date: " + (j + 1));
                        finalModel.setIfClarificationFromEmployee(true);
                    }
                }

            } else {
                //case where there is an entry
                int flag;
                for (int j = 0; j < JxcelBiometricFileWorker.month.maxLength(); j++) {
                    flag = 0;
                    //his status is still absent after merging
                    if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(ABSENT)) {
                        for (HrnetDetails hrnetDetail : finalModel.hrnetDetails) {
                            flag = setClarificationStatus(j, finalModel, hrnetDetail, "AbsentInOneNotInOther", 0);
                        }
                        if (flag == 0) {
                            System.out.println("Discrepancy Set for " + finalModel.getName() + " Date: " + (j + 1));
                            finalModel.setIfClarificationFromEmployee(true);
                        }
                    }

                    //Discrepancy if there is an entry for an employee in both Biometric and Hrnet file.
                    else if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(PRESENT)) {
                        for (HrnetDetails hrnetDetail : finalModel.hrnetDetails) {
                            setClarificationStatus(j, finalModel, hrnetDetail, "PresentInBoth", 0);
                        }
                    }

                    //Discrepancy if an employee applies for half day but works for less than four hours.
                    else if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(HALF_DAY)) {
                        if ((finalModel.attendanceOfDate[j].getWorkTimeForDay() == null) ||
                                (finalModel.attendanceOfDate[j].getWorkTimeForDay().getHour() < 4)) {
                            System.out.println("Discrepancy set for half day: " + finalModel.getName() + " Date: " + (j + 1));
                            finalModel.setIfClarificationFromEmployee(true);
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    private int setClarificationStatus(int j, FinalModel finalModel, HrnetDetails hrnetDetail, String type, int flag) {
        LocalDate startDate = hrnetDetail.attendanceOfLeave.getStartDate();
        LocalDate endDate = hrnetDetail.attendanceOfLeave.getEndDate();
        int beginHoliday = startDate.getDayOfMonth();

        if (beginHoliday == (j + 1)) {
            flag = 1;
            while (startDate.compareTo(endDate) <= 0) {
                switch (type) {
                    case "AbsentInOneNotInOther":
                        j = j + 1;
                        break;

                    case "PresentInBoth":
                        if (!hrnetDetail.attendanceOfLeave.getLeaveType().equals(WORK_FROM_HOME_IND)) {
                            System.out.println("Discrepancy set for present: " + finalModel.getName() + " Date:" + (j + 1));
                            finalModel.setIfClarificationFromEmployee(true);
                        }
                        break;
                }
                startDate = startDate.plusDays(1);
            }
        }
        return flag;
    }

}
