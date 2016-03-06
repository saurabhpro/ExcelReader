package jxcel;

import model.attendence.AttendanceOfDate;

import java.time.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SaurabhK on 09-02-2016.
 */
public class TimeManager {

	private static final int MINUTES_PER_HOUR = 60;
	private static final int SECONDS_PER_MINUTE = 60;
	private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
	private static Year year;
	private static Month month;

	public static LocalTime calculateAverageOfTime(String type, AttendanceOfDate[] attendanceOfDate) {
		List<AttendanceOfDate> ofDates = Arrays.asList(attendanceOfDate);
		int hoursTotal, minsTotal = 0, presentDays = 0;

		for (AttendanceOfDate date : ofDates) {
			if (date.getCheckIn() != null && !date.getCheckOut().equals(LocalTime.MIDNIGHT)) {
				switch (type) {
				case "AverageCheckInTime":
					if (date.getCheckIn() != null) {
						minsTotal += date.getCheckIn().getHour() * 60;
						minsTotal += date.getCheckIn().getMinute();
						presentDays++;
					}
					break;

				case "AverageCheckOutTime":
					if (date.getCheckOut() != null) {
						minsTotal += date.getCheckOut().getHour() * 60;
						minsTotal += date.getCheckOut().getMinute();
						presentDays++;
					}
					break;
				}

			}
		}
		int t = presentDays > 0 ? presentDays : 1;
		minsTotal = minsTotal / t;

		hoursTotal = minsTotal / 60;
		minsTotal = minsTotal % 60;
		return LocalTime.of(hoursTotal, minsTotal);
	}

	public static LocalTime calculateTimeDifference(LocalTime checkInTime, LocalTime checkOutTime, LocalDate date) {
		LocalDate froDate, toDate;
		froDate = toDate = date;
		LocalTime time;

		if (checkOutTime.compareTo(checkInTime) < 0)
			toDate = froDate.plusDays(1);

		LocalDateTime fromDateTime = LocalDateTime.of(froDate, checkInTime);
		LocalDateTime toDateTime = LocalDateTime.of(toDate, checkOutTime);

		time = getTime(fromDateTime, toDateTime);

		return time;
	}

	public static LocalDate convertToLocalDate(String date) {
		int year;
		int month;
		int day;

		String[] st = date.split("/");

		month = Integer.parseInt(st[0]);
		day = Integer.parseInt(st[1]);
		year = Integer.parseInt(st[2]);

		return LocalDate.of(year, month, day);
	}

	private static LocalTime convertToTime(long hr, long min) {
		return LocalTime.of((int) hr, (int) min);
	}

	public static Month getMonth() {
		return month;
	}

	public static void setMonth(Month month) {
		TimeManager.month = month;
	}

	private static LocalTime getTime(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
		Duration duration = Duration.between(fromDateTime, toDateTime);

		long seconds = duration.getSeconds();

		long hours = seconds / SECONDS_PER_HOUR;
		long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
		// long secs = (seconds % SECONDS_PER_MINUTE);

		return convertToTime(hours, minutes);

	}

	public static Year getYear() {
		return year;
	}

	public static void setYear(Year year) {
		TimeManager.year = year;
	}
}
