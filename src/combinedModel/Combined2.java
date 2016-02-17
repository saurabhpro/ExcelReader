package combinedModel;

import jxcel.BiometricFileWorker;
import jxcel.HrnetFileWorker;
import jxcel.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
            for (HolidaysList h : HolidaysList.values()) {
                if (h.getDate().getMonth() == BiometricFileWorker.month) {
                    bd.attendanceOfDate[h.getDate().getDayOfMonth() - 1].setBiometricAttendanceStatusTypes(BiometricAttendanceStatusTypes.PUBLIC_HOLIDAY);
                }
            }

            for (int i = 0; i < 31; i++) {
                switch (bd.attendanceOfDate[i].getBiometricAttendanceStatusTypes()) {
                    case ABSENT:
                        //check for Work from home and half day
                        for (HrnetDetails hr : hrnetDetails) {

                            if (hr.employeeID.equals(bd.empId)) {
                                LocalDate startDate = hr.attendanceOfLeave.getStartDate();
                                // LocalDate endDate = hr.leaveDetails.getEndDate();
                                double leaveTime = hr.attendanceOfLeave.getAbsenceTime();

                                int changeDatesRange = startDate.getDayOfMonth() - 1;

                                while (leaveTime > 0) {
                                    if (leaveTime == 0.5)
                                        bd.attendanceOfDate[changeDatesRange].setBiometricAttendanceStatusTypes(BiometricAttendanceStatusTypes.HALF_DAY);
                                    else if (hr.attendanceOfLeave.getLeaveTypes() == LeaveTypes.WORK_FROM_HOME) {
                                        bd.attendanceOfDate[changeDatesRange].setWorkTimeForDay(LocalTime.of((int) leaveTime * 8, 0));
                                        bd.attendanceOfDate[changeDatesRange].setBiometricAttendanceStatusTypes(BiometricAttendanceStatusTypes.PRESENT);
                                    }
                                    changeDatesRange++;
                                    leaveTime--;
                                }
                            }
                        }

                }
            }
        }
        BiometricFileWorker.empList = biometricDetails;
    }
}
