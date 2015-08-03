/*
 * Copyright (c) 2015 Elder Research, Inc.
 * All rights reserved.
 */
package com.datamininglab.code;

import java.util.List;

public interface CodeMetric<T> {
	T compute(List<String> lines);
	
	char getCharacter();
	String getName();
	String getFormatString();
	String getSummary();
}
