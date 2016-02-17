package combinedModel;

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

    List<BiometricDetails> newEmpList = new ArrayList<>();

    List<HrnetDetails> hrnetDetails = HrnetFileWorker.hrnetDetails;
    List<BiometricDetails> biometricDetails = BiometricFileWorker.empList;


    BiometricDetails newBiometricDetail;

    public void combineFiles() {

        for (BiometricDetails bd : biometricDetails) {

            //  for (int i = 0; i < 31; i++) {
            for (HolidaysList h : HolidaysList.values()) {
                System.out.println(h.getDate().getMonth() + " " + new BiometricFileWorker().month);
                if (h.getDate().getMonth() == new BiometricFileWorker().month) {
                    System.out.println(h.name());
                    bd.attendanceOfDate[h.getDate().getDayOfMonth() - 1].
                            setBiometricAttendanceStatusTypes(BiometricAttendanceStatusTypes.PUBLIC_HOLIDAY);
                }
                //    }

                /*
                switch (bd.attendanceOfDate[i].getBiometricAttendanceStatusTypes()) {
                    case ABSENT:
                        Iterator<HrnetDetails> hrnetDetailsIterator = hrnetDetails.iterator();

                        while (hrnetDetailsIterator.hasNext()) {
                            HrnetDetails hr = hrnetDetailsIterator.next();

                            LocalDate startDate = hr.leaveDetails.getStartDate();
                            // LocalDate endDate = hr.leaveDetails.getEndDate();

                            if (hr.hrID.equals(bd.empId)) {
                                double leaveTime = hr.leaveDetails.getAbsenceTime();

                                int changeDatesRange = startDate.getDayOfMonth() - 1;

                                if (hr.leaveDetails.getLeaveTypes() == LeaveTypes.WORK_FROM_HOME ||
                                        hr.leaveDetails.getLeaveTypes() == LeaveTypes.CASUAL_IND ||
                                        hr.leaveDetails.getLeaveTypes() == LeaveTypes.VACATION_IND) {
                                    while (leaveTime > 0) {

                                        bd.attendanceOfDate[changeDatesRange].setWorkTimeForDay(LocalTime.of((int) leaveTime * 8, 0));

                                        if (leaveTime >= 1) {
                                            bd.attendanceOfDate[changeDatesRange].setBiometricAttendanceStatusTypes(BiometricAttendanceStatusTypes.PRESENT);
                                        } else {
                                            bd.attendanceOfDate[changeDatesRange].setBiometricAttendanceStatusTypes(BiometricAttendanceStatusTypes.HALF_DAY);
                                        }

                                        changeDatesRange++;
                                        leaveTime--;
                                    }
                                }

                            }
                        }
                }*/
            }
        }
        BiometricFileWorker.empList = biometricDetails;
    }
}
