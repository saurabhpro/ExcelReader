/**
 * Created by Saurabhk on 08-02-2016.
 */

package model;

import jxcel.TimeManager;
import model.attendence.AttendanceOfDate;

import java.time.LocalTime;

public class EmpBiometricDetails extends BasicEmployeeDetails {
	public final AttendanceOfDate[] attendanceOfDate;
	public int numberOfLeaves = 0; // To check how many leaves have been applied

	public EmpBiometricDetails(String eID, String eName, AttendanceOfDate[] attendanceOfDate) {
		setName(eName);
		setEmpId(eID);
		this.attendanceOfDate = attendanceOfDate;
	}

	public int getNumberOfLeaves() {
		return numberOfLeaves;
	}

	public void setNumberOfLeaves(int numberOfLeaves) {
		this.numberOfLeaves = numberOfLeaves;
	}

	public void printEmpBiometricDetails() {
		System.out.println("Name: " + this.getName());
		System.out.println("Employee ID: " + this.getEmpId());

		LocalTime workTime;

		for (int j = 0; j < TimeManager.getMonth().maxLength(); j++) {
			System.out.print(this.attendanceOfDate[j].getCurrentDate());
			System.out.print("\tIn Time: " + this.attendanceOfDate[j].getCheckIn());
			System.out.print("\tOut Time: " + this.attendanceOfDate[j].getCheckOut());
			System.out.print("\tStatus: " + this.attendanceOfDate[j].getAttendanceStatusType() + "\n");

			workTime = this.attendanceOfDate[j].getWorkTimeForDay();
			if (workTime != null)
				System.out.println(workTime);

		}

		System.out.println();
	}
}
