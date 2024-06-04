package com.toolstore.beans.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {

	@Min(value = 1, message = "Please provide a value between 1 and 31")
	@Max(value = 31, message = "Please provide a value between 1 and 31")
	private Integer day;

	private DayOfWeek dayOfTheWeek;

	@Builder.Default
	private int weekInTheMonth = 1;

	private boolean first;
	private boolean last;

	@NotNull
	private Month month;

	@NotBlank
	private String name;

	@Transient
	private LocalDate currentYearDate;
}
