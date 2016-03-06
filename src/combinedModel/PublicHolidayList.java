package combinedModel;

import jxcel.TimeManager;
import model.attendence.AttendanceOfDate;
import model.attendence.AttendanceStatusType;
import model.attendence.HolidaysList;

import java.util.Map;

/**
 * Created by kumars on 3/4/2016.
 */
public class PublicHolidayList {


    private static Map<String, FinalModel> EmpCombinedMap;

    public PublicHolidayList() {
        EmpCombinedMap = Combined2.EmpCombinedMap;
    }

    public void presentPublicHolidayList() {
        System.out.println("HOLIDAY LIST HIGHLIGHTED");
        for (FinalModel finalModel : EmpCombinedMap.values()) {
            for (HolidaysList h : HolidaysList.values()) {
                if (h.getDate().getMonth() == TimeManager.getMonth()) {
                    if (finalModel.attendanceOfDate[h.getDate().getDayOfMonth() - 1].getAttendanceStatusType() == AttendanceStatusType.PUBLIC_HOLIDAY) {
                        if (finalModel.attendanceOfDate[h.getDate().getDayOfMonth() - 1].getCheckIn() != null)
                            //     finalModel.displayFinalList();
                            showExtraWorkTime(finalModel, h);
                    }
                }
            }
        }
    }

    private void showExtraWorkTime(FinalModel finalModel, HolidaysList h) {
        finalModel.displayBasicInfo();

        AttendanceOfDate temp = finalModel.attendanceOfDate[h.getDate().getDayOfMonth() - 1];
        System.out.println(temp.getCurrentDate() + " " + temp.getCheckIn() + " " + temp.getCheckOut()
                + " " + temp.getAttendanceStatusType() + " " + temp.getWorkTimeForDay() + " " + h.name());

    }

}
