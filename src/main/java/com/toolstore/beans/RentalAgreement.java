package com.toolstore.beans;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.sun.istack.NotNull;
import com.toolstore.beans.accounting.Price;
import com.toolstore.beans.calendar.Holiday;
import com.toolstore.beans.tools.Tool;
import com.toolstore.utils.DateUtils;

public class RentalAgreement {
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");
	public static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();
	public static final NumberFormat PERCENT_FORMATTER = NumberFormat.getPercentInstance();

	@NotNull
	private Tool tool;

	@NotNull
	@Min(value = 1, message = "Please rent for at least one day")
	private int days;

	private int chargeDays;

	private BigDecimal preDiscountCharge;
	private BigDecimal discountedAmount;
	private BigDecimal finalCharge;

	@Min(value = 0, message = "The discount must be a positive value")
	@Max(value = 100, message = "You cannot discount more that 100%")
	private int discount = 0;

	@NotNull
	private LocalDate checkoutDate;

	private LocalDate dueDate;

	public void checkout(RentalAgreement agreement, Set<Holiday> holidays) {
		dueDate = checkoutDate.plusDays(days);

		Tool tool = agreement.getTool();
		Price price = tool.getType().getPrice();
		chargeDays = DateUtils.getChargeableDays(checkoutDate, days, price, holidays);

		preDiscountCharge = price.getDailyCharge().multiply(BigDecimal.valueOf(agreement.getChargeDays()));
		discountedAmount = preDiscountCharge.multiply(BigDecimal.valueOf(agreement.getDiscount()))
				.divide(BigDecimal.valueOf(100));
		finalCharge = preDiscountCharge.subtract(discountedAmount);
	}

	public synchronized void print() {
		if (finalCharge == null) {
			throw new IllegalStateException("Please checkout first!");
		}

		Price price = tool.getType().getPrice();

		System.out.println("\nTool code: " + tool.getCode());
		System.out.println("Tool type: " + tool.getType().getName());
		System.out.println("Tool brand: " + tool.getBrand());
		System.out.println("Rental days: " + chargeDays);
		System.out.println("Checkout date: " + DATE_FORMATTER.format(checkoutDate));
		System.out.println("Due date: " + DATE_FORMATTER.format(dueDate));
		System.out.println("Daily charge: " + CURRENCY_FORMATTER.format(price.getDailyCharge()));
		System.out.println("Pre-discount charge: " + CURRENCY_FORMATTER.format(preDiscountCharge));
		System.out.println("Discount percent: " + PERCENT_FORMATTER.format(discount * 1.0 / 100));
		System.out.println("Discount amount: " + CURRENCY_FORMATTER.format(discountedAmount));
		System.out.println("Final charge: " + CURRENCY_FORMATTER.format(finalCharge));
	}

	public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public int getChargeDays() {
		return chargeDays;
	}

	public BigDecimal getPreDiscountCharge() {
		return preDiscountCharge;
	}

	public BigDecimal getDiscountedAmount() {
		return discountedAmount;
	}

	public BigDecimal getFinalCharge() {
		return finalCharge;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

}
