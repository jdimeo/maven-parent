/*******************************************************************************
 * Copyright (c) 2016 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.code;

import lombok.Getter;

public abstract class CodeFlag implements CodeMetric<String> {
	private static final float PCT = 100.0f;
	
	@Getter private String code;
	@Getter private String name;
	private int matched, total;
	
	protected CodeFlag(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	@Override
	public String getFormatString() { return " %s "; }
	
	protected String toString(boolean result) {
		total++;
		if (result) {
			matched++;
			return "Y";
		}
		return "!";
	}
	
	@Override
	public String getSummary() {
		return String.format("%3d out of %3d (%.1f%%)", matched, total, PCT * matched / total);
	}
}
