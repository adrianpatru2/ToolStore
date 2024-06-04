package com.toolstore.beans.tools;

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
public class Tool {

	@NotNull
	private ToolType type;

	@NotBlank
	private String code;

	private String brand;

}
