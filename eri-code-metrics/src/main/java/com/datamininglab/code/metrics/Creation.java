/*******************************************************************************
 * Copyright (c) 2016 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.datamininglab.code.metrics;

import java.util.List;

import com.datamininglab.code.CodeFlag;

public class Creation extends CodeFlag {
	public Creation() { super(" # ", "Has date of creation information"); }
	
	@Override
	public String compute(List<String> lines) {
		for (String line : lines) {
			if (line.toLowerCase().contains("@since")) { return toString(true); }
		}
		return toString(false);
	}
}
