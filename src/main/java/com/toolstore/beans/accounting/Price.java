package com.toolstore.beans.accounting;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price {
	@NotNull
	private BigDecimal dailyCharge;

	@Builder.Default
	private boolean applyingOnWeekdays = true;

	private boolean applyingOnWeekends;
	private boolean applyingOnHolidays;
}
