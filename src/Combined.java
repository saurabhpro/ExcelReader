import jxcel.BiometricFileWorker;
import jxcel.HrnetFileWorker;
import jxcel.model.BiometricAttendanceStatusTypes;
import jxcel.model.BiometricDetails;
import jxcel.model.HrnetDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Saurabhk on 15-02-2016.
 */
public class Combined {

    List<BiometricDetails> newEmpList = new ArrayList<>();

    List<HrnetDetails> hrnetDetails = HrnetFileWorker.hrnetDetails;
    List<BiometricDetails> biometricDetails = BiometricFileWorker.empList;

    Iterator<HrnetDetails> iterator = hrnetDetails.iterator();
    Iterator<BiometricDetails> biometricDetailsIterator = biometricDetails.iterator();

    BiometricDetails newBiometricDetail;

    void combineFiles() {

        LocalDate startTime = null;
        LocalDate endTime = null;

        while (iterator.hasNext()) {
            HrnetDetails hr = iterator.next();

            while (biometricDetailsIterator.hasNext()) {
                BiometricDetails bd = biometricDetailsIterator.next();

                if (bd.empId.equals(hr.hrID)) {
                    String empName = bd.name;
                    String empId = bd.empId;

                    startTime = hr.leaveDetails.getStartDate();
                    for (int i = 0; i < 31; i++) {

                        if (bd.attendanceOfDate[i].getBiometricAttendanceStatusTypes() == BiometricAttendanceStatusTypes.ABSENT) {
                            switch (hr.leaveDetails.getLeaveTypes()) {
                                case WORK_FROM_HOME:
                                    LocalTime tmpHrs = LocalTime.of((int) hr.leaveDetails.getAbsenceTime() * 8, 0);
                                    LocalTime tmp = LocalTime.of(9, 30);
                                    newBiometricDetail.attendanceOfDate[i].setWorkTimeForDay(tmpHrs);
                                    newBiometricDetail.attendanceOfDate[i].setBiometricAttendanceStatusTypes(BiometricAttendanceStatusTypes.PRESENT);
                                    newBiometricDetail.attendanceOfDate[i].setCheckIn(tmp);
                                    newBiometricDetail.attendanceOfDate[i].setCheckOut(tmp.plusHours(tmpHrs.getHour()));
                                    newBiometricDetail.attendanceOfDate[i].setCurrentDate(bd.attendanceOfDate[i].getCurrentDate());
                                    newBiometricDetail.setIfClarificationFromEmployee(false);
                                    break;
                                case CASUAL_IND:
                                    newBiometricDetail.attendanceOfDate[i].setWorkTimeForDay(LocalTime.of((int) hr.leaveDetails.getAbsenceTime() * 8, 0));
                            }
                        }

                    }


                    newBiometricDetail = new BiometricDetails(bd.name, bd.empId, newBiometricDetail.attendanceOfDate);


                }
            }


        }
    }

}
