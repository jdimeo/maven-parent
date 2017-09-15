/*******************************************************************************
 * Copyright (c) 2016 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.code.metrics;

import java.util.List;

import com.elderresearch.code.CodeFlag;

public class Authorship extends CodeFlag {
	public Authorship() { super(" @ ", "Has authorship information"); }
	
	@Override
	public String compute(List<String> lines) {
		for (String line : lines) {
			if (line.toLowerCase().contains("@author")) { return toString(true); }
		}
		return toString(false);
	}
}
