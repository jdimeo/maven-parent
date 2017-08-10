/*******************************************************************************
 * Copyright (c) 2016 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.datamininglab.code;

import lombok.Getter;

public abstract class CodeCount implements CodeMetric<Integer> {
	@Getter private String code;
	@Getter private String name;
	protected int total;
	
	protected CodeCount(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	@Override
	public String getFormatString() { return "%3d"; }
	
	@Override
	public String getSummary() { return String.format("%3d total", total); }
}
