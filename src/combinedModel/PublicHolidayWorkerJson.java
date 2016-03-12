package combinedModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import jxcel.TimeManager;
import model.FinalModel;
import model.HolidayWorkerModel;
import model.JSONModelForWeb;
import model.ListGenerator;
import model.attendence.HolidaysList;

import java.io.File;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static model.attendence.AttendanceStatusType.PUBLIC_HOLIDAY;

/**
 * Created by kumars on 3/4/2016.
 */
public class PublicHolidayWorkerJson extends ListGenerator {
    List<HolidayWorkerModel> holidayWorkerList = new ArrayList<>();

    @Override
    public void generate() {
        for (FinalModel finalModel : allEmpDetails.values()) {
            for (HolidaysList h : HolidaysList.values()) {
                Month curMnth = h.getDate().getMonth();
                int changeOnDate = h.getDate().getDayOfMonth() - 1;
                if (curMnth == TimeManager.getMonth() &&
                        finalModel.attendanceOfDate[changeOnDate].getAttendanceStatusType() == PUBLIC_HOLIDAY &&
                        finalModel.attendanceOfDate[changeOnDate].getCheckIn() != null) {
                    HolidayWorkerModel h1 = new JSONModelForWeb(finalModel).getHolidayWorkerObjForThisDate(changeOnDate);
                    if (h1 != null) {
                        h1.setHoliday(h);
                        holidayWorkerList.add(h1);
                    }
                }
            }
        }
    }

    @Override
    public void displayOnConsole() {
        System.out.println("HOLIDAY LIST HIGHLIGHTED");
        holidayWorkerList.forEach(HolidayWorkerModel::display);
    }

    @Override
    public void createJSONList(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        // For testing
        List<HolidayWorkerModel> user = holidayWorkerList;

        try {
            File jfile = new File(".\\JSON files\\" + fileName + ".json");
            // Convert object to JSON string and save into file directly
            mapper.writeValue(jfile, user);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
