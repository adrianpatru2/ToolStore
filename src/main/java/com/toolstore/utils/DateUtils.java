package com.toolstore.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;
import java.util.stream.IntStream;

import com.toolstore.beans.accounting.Price;
import com.toolstore.beans.calendar.Holiday;

public class DateUtils {
	public static boolean isWeekend(LocalDate date) {
		switch (date.getDayOfWeek()) {
		case SATURDAY:
		case SUNDAY:
			return true;
		default:
			break;
		}
		return false;
	}

	public static int getChargeableDays(LocalDate checkout, int days, Price price, Set<Holiday> holidays) {
		return IntStream.range(1, days + 1) //
				.map(idx -> {
					LocalDate date = checkout.plusDays(idx);
					if (!price.isApplyingOnWeekends() && isWeekend(date)) {
						return 0;
					} else if (!price.isApplyingOnHolidays() && isHoliday(date, holidays)) {
						return 0;
					}
					return 1;
				}).sum();
	}

	public static LocalDate getHolidayDateInCurrentYear(Holiday holiday) {
		return getHolidayDateInYear(LocalDate.now().getYear(), holiday);
	}

	public static LocalDate getHolidayDateInYear(int year, Holiday holiday) {
		if (holiday.getDayOfTheWeek() != null) {
			LocalDate first = LocalDate.of(year, holiday.getMonth(), 1);

			return holiday.isFirst() ? first.with(TemporalAdjusters.firstInMonth(holiday.getDayOfTheWeek()))
					: holiday.isLast() ? first.with(TemporalAdjusters.lastInMonth(holiday.getDayOfTheWeek()))
							: first.with(TemporalAdjusters.dayOfWeekInMonth(holiday.getWeekInTheMonth(),
									holiday.getDayOfTheWeek()));
		}

		if (holiday.getDay() == null) {
			throw new IllegalArgumentException(
					"Please provide either a dayOfTheWeek or a day for " + holiday.getName() + " holiday");
		}

		LocalDate date = LocalDate.of(year, holiday.getMonth(), holiday.getDay());
		if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
			return date.minusDays(1);
		}

		if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			return date.plusDays(1);
		}

		return date;
	}

	public static boolean isHoliday(LocalDate date, Set<Holiday> holidays) {
		return holidays.stream().anyMatch(h -> {
			if (h.getCurrentYearDate() == null) {
				h.setCurrentYearDate(getHolidayDateInCurrentYear(h));
			}
			return h.getCurrentYearDate().equals(date);
		});
	}
}
