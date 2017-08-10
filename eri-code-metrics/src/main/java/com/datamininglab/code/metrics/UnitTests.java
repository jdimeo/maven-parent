/*******************************************************************************
 * Copyright (c) 2016 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.datamininglab.code.metrics;

import java.util.List;

import com.datamininglab.code.CodeCount;

public class UnitTests extends CodeCount {
	public UnitTests() { super(" U ", "Number of unit tests"); }
	
	@Override
	public Integer compute(List<String> lines) {
		int ret = 0;
		for (String line : lines) {
			if (line.trim().startsWith("public void test")
			 || line.trim().startsWith("it(")) { ret++; }
		}
		total += ret;
		return ret;
	}
}
