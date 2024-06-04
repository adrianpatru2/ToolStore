package com.toolstore.utils;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import com.toolstore.beans.accounting.Price;
import com.toolstore.beans.calendar.Holiday;

public class DateUtilsTest {
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");

	@Test
	public void holiday_whenADate() {
		Holiday holiday = DateFixture.INDEPENDENCE_DAY;
		LocalDate day = DateUtils.getHolidayDateInCurrentYear(holiday);
		assertEquals("07/04/24", DATE_FORMATTER.format(day));

		day = DateUtils.getHolidayDateInYear(2023, holiday);
		assertEquals("07/04/23", DATE_FORMATTER.format(day));

		day = DateUtils.getHolidayDateInYear(2021, holiday);
		assertEquals("07/05/21", DATE_FORMATTER.format(day));
		assertEquals(DayOfWeek.MONDAY, day.getDayOfWeek());

		day = DateUtils.getHolidayDateInYear(2026, holiday);
		assertEquals("07/03/26", DATE_FORMATTER.format(day));
		assertEquals(DayOfWeek.FRIDAY, day.getDayOfWeek());
	}

	@Test
	public void holiday_whenADayOfWeek() {
		Holiday holiday = DateFixture.LABOUR_DAY;
		LocalDate day = DateUtils.getHolidayDateInCurrentYear(holiday);
		assertEquals("09/02/24", DATE_FORMATTER.format(day));

		day = DateUtils.getHolidayDateInYear(2023, holiday);
		assertEquals("09/04/23", DATE_FORMATTER.format(day));

		day = DateUtils.getHolidayDateInYear(2021, holiday);
		assertEquals("09/06/21", DATE_FORMATTER.format(day));
	}

	@Test
	public void getChargeableDays() {
		LocalDate checkout = LocalDate.of(2024, Month.JUNE, 25);
		Price price = Price.builder().dailyCharge(BigDecimal.valueOf(1.49)).build();
		int days = DateUtils.getChargeableDays(checkout, 10, price, DateFixture.HOLIDAYS);
		assertEquals(7, days);

		///
		price.setApplyingOnHolidays(true);
		days = DateUtils.getChargeableDays(checkout, 10, price, DateFixture.HOLIDAYS);
		assertEquals(8, days);

		///
		price.setApplyingOnHolidays(false);
		price.setApplyingOnWeekends(true);
		days = DateUtils.getChargeableDays(checkout, 10, price, DateFixture.HOLIDAYS);
		assertEquals(9, days);
	}
}
