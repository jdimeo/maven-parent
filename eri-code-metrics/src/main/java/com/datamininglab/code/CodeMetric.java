/*******************************************************************************
 * Copyright (c) 2016 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.datamininglab.code;

import java.util.List;

public interface CodeMetric<T> {
	T compute(List<String> lines);
	
	String getCode();
	String getName();
	String getFormatString();
	String getSummary();
}
