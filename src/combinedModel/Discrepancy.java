package combinedModel;

import jxcel.model.HrnetDetails;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;

import static jxcel.model.AttendanceStatusType.*;
import static jxcel.model.LeaveType.WORK_FROM_HOME;

/**
 * Created by AroraA on 17-02-2016.
 */

public class Discrepancy {

    Map<String, FinalModel> newEmpMap = Combined2.newEmpMap;
    Iterator<FinalModel> finalModelIterator = newEmpMap.values().iterator();
    LocalDate startDate = null;
    LocalDate endDate = null;

    FinalModel finalModel;

    Iterator<HrnetDetails> iterator2;

    public void findDiscrepancy() {

        while (finalModelIterator.hasNext()) {
            finalModel = finalModelIterator.next();

            //Discrepancy if an employee is absent and there is no entry in Hrnet file.
            if (finalModel.hrnetDetails == null) {
                for (int j = 0; j < 31; j++) {
                    if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(ABSENT)) {
                        System.out.println("Null List- Discrepancy Set for " + finalModel.name + " Date: " + (j + 1));
                        finalModel.setIfClarificationFromEmployee(true);
                    }

                }

            } else {
                int flag = 0;
                for (int j = 0; j < 31; j++) {

                    Iterator<HrnetDetails> hrnetDetailsIterator;
                    hrnetDetailsIterator = finalModel.hrnetDetails.iterator();

                    //System.out.println(hrnetDetailsIterator.next().attendanceOfLeave.getStartDate());
                    if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(ABSENT)) {

                        int dayOfMonth;
                        HrnetDetails hrnetDetails;
                        while (hrnetDetailsIterator.hasNext()) {
                            hrnetDetails = hrnetDetailsIterator.next();

                            startDate = hrnetDetails.attendanceOfLeave.getStartDate();
                            endDate = hrnetDetails.attendanceOfLeave.getEndDate();

                            dayOfMonth = startDate.getDayOfMonth();


                            if ((dayOfMonth) == (j + 1)) {
                                flag = 1;
                                while (startDate.compareTo(endDate) <= 0) {

                                    if (!hrnetDetails.attendanceOfLeave.getLeaveType().equals(WORK_FROM_HOME)) {
                                        j = j + 1;
                                        startDate = startDate.plusDays(1);
                                        continue;
                                    } else {
                                        System.out.println("Discrepancy Set for " + hrnetDetails.getName() + " Date: " + (dayOfMonth));
                                        finalModel.setIfClarificationFromEmployee(true);
                                    }
                                    j = j + 1;
                                    startDate = startDate.plusDays(1);

                                }

                            }

                        }
                        if (flag == 0) {
                            System.out.println("Discprepancy Set for " + finalModel.name + " Date: " + (j + 1));
                            finalModel.setIfClarificationFromEmployee(true);
                        }
                        flag = 0;
                    }
                    //Discrepancy if there is an entry for an employee in both Biometric and Hrnet file.
                    else if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(PRESENT)) {

                        iterator2 = finalModel.hrnetDetails.iterator();
                        HrnetDetails hrnetDetails;

                        while (iterator2.hasNext()) {

                            hrnetDetails = iterator2.next();
                            startDate = hrnetDetails.attendanceOfLeave.getStartDate();
                            endDate = hrnetDetails.attendanceOfLeave.getEndDate();
                            int dayOfMonth = startDate.getDayOfMonth();
                            if (dayOfMonth == (j + 1)) {
                                while (startDate.compareTo(endDate) <= 0) {
                                    if (!hrnetDetails.attendanceOfLeave.getLeaveType().equals(WORK_FROM_HOME)) {
                                        System.out.println("Discrepancy set for present: " + finalModel.name + " Date:" + (j + 1));
                                        finalModel.setIfClarificationFromEmployee(true);
                                    }
                                    startDate = startDate.plusDays(1);
                                }
                            }
                        }
                    }
                    //Discrepancy if an employee applies for half day but works for less than four hours.
                    else if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(HALF_DAY)) {

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
