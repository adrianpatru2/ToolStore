package com.toolstore.beans.tools;

import com.toolstore.beans.accounting.Price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolType {
	private String name;
	private Price price;
}
