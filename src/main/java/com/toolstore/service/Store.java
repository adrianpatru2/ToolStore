package com.toolstore.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.toolstore.beans.RentalAgreement;
import com.toolstore.beans.calendar.Holiday;
import com.toolstore.beans.tools.Tool;
import com.toolstore.beans.tools.ToolType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store {

	private List<ToolType> toolTypes;

	private Set<Holiday> holidays;

	@Builder.Default
	private Map<String, Tool> tools = new HashMap<>();

	public synchronized void addTool(String brand, String code, ToolType toolType) {
		getTools().put(code, Tool.builder() //
				.code(code) //
				.type(toolType) //
				.brand(brand) //
				.build());
	}

	public Tool getTool(String code) {
		return getTools().get(code);
	}

	public void checkout(RentalAgreement agreement) {
		agreement.checkout(agreement, holidays);
		agreement.print();
	}
}
