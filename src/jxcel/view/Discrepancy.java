package jxcel.view;

import combinedModel.Combined2;
import combinedModel.FinalModel;
import jxcel.model.AttendanceStatusType;
import jxcel.model.HrnetDetails;
import jxcel.model.LeaveType;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

/**
 * Created by AroraA on 17-02-2016.
 */

public class Discrepancy {

    List<FinalModel> newEmpList = Combined2.newEmpList;
    Iterator<FinalModel> iterator = newEmpList.iterator();
    LocalDate startDate = null;
    LocalDate endDate = null;

    FinalModel finalModel;
    Iterator<HrnetDetails> iterator1;
    Iterator<HrnetDetails> iterator2;

    public void findDiscrepancy() {

        while (iterator.hasNext()) {
            finalModel = iterator.next();

            //Discrepancy if an employee is absent and there is no entry in Hrnet file.
            if (finalModel.empList1 == null) {
                for (int j = 0; j < 31; j++) {
                    if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(AttendanceStatusType.ABSENT)) {
                        System.out.println("Null List- Discrepancy Set for " + finalModel.name + " Date: " + (j + 1));
                        finalModel.setIfClarificationFromEmployee(true);
                    }

                }

            } else {
                int flag = 0;
                for (int j = 0; j < 31; j++) {

                    if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(AttendanceStatusType.ABSENT)) {

                        iterator1 = finalModel.empList1.iterator();

                        int dayOfMonth;

                        while (iterator1.hasNext()) {
                            HrnetDetails hrnetDetails;
                            hrnetDetails = iterator1.next();

                            startDate = hrnetDetails.attendanceOfLeave.getStartDate();
                            endDate = hrnetDetails.attendanceOfLeave.getEndDate();
                            dayOfMonth = startDate.getDayOfMonth();

                            System.out.println(dayOfMonth);
                            double absenceTime = hrnetDetails.attendanceOfLeave.getAbsenceTime();
                            if ((dayOfMonth) == (j + 1)) {
                                flag = 1;
                                while (absenceTime <= 0) {

                                    if (!hrnetDetails.attendanceOfLeave.getLeaveType().equals(LeaveType.WORK_FROM_HOME)) {

                                        absenceTime = absenceTime - 1;
                                        continue;
                                    } else {
                                        System.out.println("Discrepancy Set for " + hrnetDetails.name + " Date: " + (dayOfMonth));
                                        finalModel.setIfClarificationFromEmployee(true);

                                    }
                                    absenceTime = absenceTime - 1;
                                }

                            }

                        }
                        if (flag == 0) {
                            System.out.println("Discprepancy Set for (flag)" + finalModel.name + " Date: " + (j + 1));
                            finalModel.setIfClarificationFromEmployee(true);
                        }
                        flag = 0;
                    }
                    //Discrepancy if there is an entry for an employee in both Biometric and Hrnet file.
                    else if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(AttendanceStatusType.PRESENT)) {

                        iterator2 = finalModel.empList1.iterator();
                        HrnetDetails hrnetDetails;

                        while (iterator2.hasNext()) {

                            hrnetDetails = iterator2.next();
                            startDate = hrnetDetails.attendanceOfLeave.getStartDate();
                            endDate = hrnetDetails.attendanceOfLeave.getEndDate();
                            int dayOfMonth = startDate.getDayOfMonth();
                            if (dayOfMonth == (j + 1)) {
                                while (hrnetDetails.attendanceOfLeave.getAbsenceTime() <= 1) {
                                    if (!hrnetDetails.attendanceOfLeave.getLeaveType().equals(LeaveType.WORK_FROM_HOME)) {
                                        System.out.println("Discrepancy set for present: " + finalModel.name + " Date:" + (j + 1));
                                        finalModel.setIfClarificationFromEmployee(true);
                                    }
                                    startDate = startDate.plusDays(1);
                                }
                            }
                        }
                    }
                    //Discrepancy if an employee applies for half day but works for less than four hours.
                    else if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(AttendanceStatusType.HALF_DAY)) {

                        if ((finalModel.attendanceOfDate[j].getWorkTimeForDay() == null) || (finalModel.attendanceOfDate[j].getWorkTimeForDay().getHour() < 4)) {
                            System.out.println("Discrepancy set for half day: " + finalModel.name + " Date: " + (j + 1));
                            finalModel.setIfClarificationFromEmployee(true);
                        }
                    }
                }
            }
            System.out.println();
        }
    }
}
