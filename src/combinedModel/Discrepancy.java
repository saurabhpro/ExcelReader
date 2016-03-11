package combinedModel;

import jxcel.TimeManager;
import model.HrnetDetails;

import java.time.LocalDate;
import java.util.Map;

import static model.attendence.AttendanceStatusType.*;
import static model.attendence.LeaveType.WORK_FROM_HOME_IND;

/**
 * Created by AroraA on 17-02-2016. 6-03-2016 changed the Type from ABSENT to
 * UNACCOUNTED_ABSENCE.
 */

public class Discrepancy {
	public static Map<String, FinalModel> EmpCombinedMap;

	public void findDiscrepancy() {
		EmpCombinedMap = Combined2.EmpCombinedMap;
		for (FinalModel finalModel : EmpCombinedMap.values()) {
			// Discrepancy if an employee is absent and there is no entry in
			// Hrnet file.
			if (finalModel.hrnetDetails == null) {
				for (int j = 0; j < TimeManager.getMonth().maxLength(); j++) {
					if ((finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(UNACCOUNTED_ABSENCE))
							|| (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(HALF_DAY))) {
						System.out.println(
								"Null List- Discrepancy Set for " + finalModel.getName() + " Date: " + (j + 1));
						finalModel.setIfClarificationNeeded(true);
					}
				}

			} else {
				// case where there is an entry
				int flag;
				for (int j = 0; j < TimeManager.getMonth().maxLength(); j++) {
					flag = 0;
					// his status is still absent after merging
					if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(UNACCOUNTED_ABSENCE)) {

						int[] temp;
						for (HrnetDetails hrnetDetail : finalModel.hrnetDetails) {
							temp = setClarificationStatus(j, finalModel, hrnetDetail, "AbsentInOneNotInOther", 0);
							j = temp[0];
							flag = temp[1];
							if (flag == 1)
								break;
						}
						if (flag == 0) {
							System.out.println("Discrepancy Set for " + finalModel.getName() + " Date: " + (j + 1));
							finalModel.setIfClarificationNeeded(true);
						}
					}

					// Discrepancy if there is an entry for an employee in both
					// Biometric and Hrnet file.
					else if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(PRESENT)) {
						for (HrnetDetails hrnetDetail : finalModel.hrnetDetails) {
							setClarificationStatus(j, finalModel, hrnetDetail, "PresentInBoth", 0);
						}
					}

					// Discrepancy if an employee applies for half day but works
					// for less than four hours.
					else if (finalModel.attendanceOfDate[j].getAttendanceStatusType().equals(HALF_DAY)) {

						for (HrnetDetails hrnetDetail : finalModel.hrnetDetails) {
							if (hrnetDetail.attendanceOfLeave.getStartDate().getDayOfMonth() == j + 1) {
								if ((finalModel.attendanceOfDate[j].getWorkTimeForDay() == null)
										|| (finalModel.attendanceOfDate[j].getWorkTimeForDay().getHour() < 4)) {
									System.out.println("Discrepancy set for half day less than 4: "
											+ finalModel.getName() + " Date: " + (j + 1));
									finalModel.setIfClarificationNeeded(true);
								}
							}
						}
					}
				}
			}
			System.out.println();
		}
	}

	private int[] setClarificationStatus(int j, FinalModel finalModel, HrnetDetails hrnetDetail, String type, int flag) {
		LocalDate startDate = hrnetDetail.attendanceOfLeave.getStartDate();
		LocalDate endDate = hrnetDetail.attendanceOfLeave.getEndDate();

		int beginHoliday = startDate.getDayOfMonth();

		if (beginHoliday == (j + 1)) {
			flag = 1;

			while (startDate.compareTo(endDate) <= 0) {
				switch (type) {

				case "AbsentInOneNotInOther":
					j++;
					break;

				case "PresentInBoth":
					if (!hrnetDetail.attendanceOfLeave.getLeaveType().equals(WORK_FROM_HOME_IND)) {
						System.out.println("Discrepancy set for present: " + finalModel.getName() + " Date:" + (j + 1));
						finalModel.setIfClarificationNeeded(true);
					}
					break;
				}
				startDate = startDate.plusDays(1);
			}
		}
		return new int[] { j, flag };
	}

}
