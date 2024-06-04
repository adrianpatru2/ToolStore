package com.toolstore.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.toolstore.beans.RentalAgreement;
import com.toolstore.beans.accounting.Price;
import com.toolstore.beans.tools.ToolType;
import com.toolstore.utils.DateFixture;

public class StoreTest {
	public static final String CHAINSAW1 = "CHNS";
	public static final String LADDER1 = "LADW";
	public static final String JACKHAMMER1 = "JAKD";
	public static final String JACKHAMMER2 = "JAKR";

	public static final ToolType CHAINSAW = ToolType.builder()//
			.name("Chainsaw") //
			.price(Price.builder().dailyCharge(BigDecimal.valueOf(1.49)).applyingOnHolidays(true).build()).build();

	public static final ToolType LADDER = ToolType.builder() //
			.name("Ladder") //
			.price(Price.builder().dailyCharge(BigDecimal.valueOf(1.99)).applyingOnWeekends(true).build()) //
			.build();

	public static final ToolType JACKHAMMER = ToolType.builder() //
			.name("Jackhammer") //
			.price(Price.builder().dailyCharge(BigDecimal.valueOf(2.99)).build()) //
			.build();

	public static final List<ToolType> TOOLTYPES = Stream.of(CHAINSAW, LADDER, JACKHAMMER).collect(Collectors.toList());

	private Store store;

	@Before
	public void before() {
		store = createStore();
	}

	@Test
	public void chainsaw() {
		RentalAgreement agreement = new RentalAgreement();
		agreement.setDays(10);
		agreement.setCheckoutDate(LocalDate.of(2024, Month.JUNE, 4));
		agreement.setTool(store.getTool(CHAINSAW1));
		store.checkout(agreement);

		double diff = 0.01;
		assertEquals(8, agreement.getChargeDays());
		assertEquals(11.92, agreement.getPreDiscountCharge().doubleValue(), diff);
		assertEquals(0.0, agreement.getDiscountedAmount().doubleValue(), diff);
		assertEquals(11.92, agreement.getFinalCharge().doubleValue(), diff);
	}

	@Test
	public void ladder() {
		RentalAgreement agreement = new RentalAgreement();
		agreement.setDays(10);
		agreement.setCheckoutDate(LocalDate.of(2024, Month.AUGUST, 31));
		agreement.setTool(store.getTool(LADDER1));
		agreement.setDiscount(15);
		store.checkout(agreement);

		double diff = 0.01;
		assertEquals(9, agreement.getChargeDays());
		assertEquals(17.91, agreement.getPreDiscountCharge().doubleValue(), diff);
		assertEquals(2.69, agreement.getDiscountedAmount().doubleValue(), diff);
		assertEquals(15.22, agreement.getFinalCharge().doubleValue(), diff);
	}

	@Test
	public void jackhammer() {
		RentalAgreement agreement = new RentalAgreement();
		agreement.setDays(10);
		agreement.setCheckoutDate(LocalDate.of(2024, Month.JULY, 2));
		agreement.setTool(store.getTool(JACKHAMMER1));
		agreement.setDiscount(10);
		store.checkout(agreement);

		double diff = 0.01;
		assertEquals(7, agreement.getChargeDays());
		assertEquals(20.93, agreement.getPreDiscountCharge().doubleValue(), diff);
		assertEquals(2.09, agreement.getDiscountedAmount().doubleValue(), diff);
		assertEquals(18.84, agreement.getFinalCharge().doubleValue(), diff);
	}

	private static Store createStore() {
		Store store = Store.builder() //
				.holidays(DateFixture.HOLIDAYS)//
				.toolTypes(TOOLTYPES) //
				.build();

		store.addTool("Stihl", CHAINSAW1, CHAINSAW);
		store.addTool("Werner", LADDER1, LADDER);
		store.addTool("DeWalt", JACKHAMMER1, JACKHAMMER);
		store.addTool("Ridgid", JACKHAMMER2, JACKHAMMER);

		return store;
	}
}
