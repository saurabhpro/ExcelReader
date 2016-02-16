package combinedModel;

import jxcel.BiometricFileWorker;
import jxcel.HrnetFileWorker;
import jxcel.model.BiometricAttendanceStatusTypes;
import jxcel.model.BiometricDetails;
import jxcel.model.HrnetDetails;
import jxcel.model.LeaveTypes;

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

    Iterator<HrnetDetails> hrnetDetailsIterator = hrnetDetails.iterator();
    Iterator<BiometricDetails> biometricDetailsIterator = biometricDetails.iterator();

    BiometricDetails newBiometricDetail;

    void combineFiles() {

        while (biometricDetailsIterator.hasNext()) {
            BiometricDetails bd = biometricDetailsIterator.next();

            for (int i = 0; i < 31; i++) {
                switch (bd.attendanceOfDate[i].getBiometricAttendanceStatusTypes()) {
                    case ABSENT:
                        while (hrnetDetailsIterator.hasNext()) {
                            HrnetDetails hr = hrnetDetailsIterator.next();

                            LocalDate startDate = hr.leaveDetails.getStartDate();
                            // LocalDate endDate = hr.leaveDetails.getEndDate();

                            if (hr.hrID.equals(bd.empId)) {

                                double leaveTime = hr.leaveDetails.getAbsenceTime();

                                int changeDatesRange = startDate.getDayOfMonth();

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
                }


            }
        }


        LocalDate startTime = null;
        LocalDate endTime = null;


    }
}
