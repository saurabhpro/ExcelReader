package jxcel.view;

import combinedModel.Combined2;
import combinedModel.FinalModel;
import jxcel.model.AttendanceStatusType;
import jxcel.model.HrnetDetails;
import jxcel.model.LeaveTypes;

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

    public void findDiscrepancy() {

        while (iterator.hasNext()) {
            FinalModel finalModel = iterator.next();
            if (finalModel.empList1 == null) {
                for (int j = 0; j < 31; j++) {
                    if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(AttendanceStatusType.ABSENT)) {
                        finalModel.setIfClarificationFromEmployee(true);
                    }

                }

            } else {
                for (int j = 0; j < 31; j++) {

                    if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(AttendanceStatusType.ABSENT)) {

                        Iterator<HrnetDetails> iterator1 = finalModel.empList1.iterator();
                        HrnetDetails hrnetDetails;
                        while (iterator1.hasNext()) {
                            hrnetDetails = iterator1.next();
                            int dayOfWeek;
                            startDate = hrnetDetails.attendanceOfLeave.getStartDate();
                            endDate = hrnetDetails.attendanceOfLeave.getEndDate();
                            while (startDate.compareTo(endDate) <= 0) {

                                dayOfWeek = startDate.getDayOfMonth();

                                if ((dayOfWeek - 1) == j) {
                                    if (hrnetDetails.attendanceOfLeave.getLeaveTypes().equals(LeaveTypes.CASUAL_IND) || hrnetDetails.attendanceOfLeave.getLeaveTypes().equals(LeaveTypes.SICK_LEAVE) || hrnetDetails.attendanceOfLeave.getLeaveTypes().equals(LeaveTypes.VACATION_IND)) {
                                        startDate = startDate.plusDays(1);
                                        continue;
                                    } else {
                                        System.out.println("Discrepancy" + startDate);
                                        finalModel.setIfClarificationFromEmployee(true);

                                    }
                                } else {
                                    System.out.println("Discrepancy" + hrnetDetails.name);
                                    finalModel.setIfClarificationFromEmployee(true);

                                }

                                startDate = startDate.plusDays(1);

                            }

                        }
                    }
                    /*
                    else if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(AttendanceStatusType.PRESENT)) {
                        Iterator<HrnetDetails> iterator2 = finalModel.empList1.iterator();
                        HrnetDetails hrnetDetails;
                        while (iterator2.hasNext()) {
                                hrnetDetails = iterator2.next();
                                startDate = hrnetDetails.attendanceOfLeave.getStartDate();
                                endDate = hrnetDetails.attendanceOfLeave.getEndDate();
                                while (startDate.compareTo(endDate) == 0) {
                                    DayOfWeek dayOfWeek = startDate.getDayOfWeek();
                                    if (dayOfWeek.equals((j + 1))) {
                                        if (hrnetDetails.attendanceOfLeave.getLeaveTypes().equals(LeaveTypes.CASUAL_IND) || hrnetDetails.attendanceOfLeave.getLeaveTypes().equals(LeaveTypes.SICK_LEAVE) || hrnetDetails.attendanceOfLeave.getLeaveTypes().equals(LeaveTypes.VACATION_IND)) {
                                            finalModel.setIfClarificationFromEmployee(true);

                                        }
                                    }
                                    startDate.plusDays(1);
                                }
                            }
                        }

                        if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(AttendanceStatusType.HALF_DAY)) {
                            if ((finalModel.attendanceOfDate[j].getWorkTimeForDay().getHour()) < 4)
                                finalModel.setIfClarificationFromEmployee(true);
                        }
                    }
                    */
                }

            }
        }
    }
}
