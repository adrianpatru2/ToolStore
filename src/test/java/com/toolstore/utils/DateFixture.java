package com.toolstore.utils;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.toolstore.beans.calendar.Holiday;

public class DateFixture {
	public static final Holiday INDEPENDENCE_DAY = Holiday.builder().day(4).month(Month.JULY).build();
	public static final Holiday MEMORIAL_DAY = Holiday.builder().month(Month.MAY).dayOfTheWeek(DayOfWeek.MONDAY)
			.last(true).build();
	public static final Holiday LABOUR_DAY = Holiday.builder().month(Month.SEPTEMBER).dayOfTheWeek(DayOfWeek.MONDAY)
			.weekInTheMonth(1).build();

	public static final Set<Holiday> HOLIDAYS = Stream.of(MEMORIAL_DAY, INDEPENDENCE_DAY, LABOUR_DAY)
			.collect(Collectors.toSet());

}
