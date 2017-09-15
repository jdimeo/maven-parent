/*******************************************************************************
 * Copyright (c) 2016 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.code.metrics;

import java.util.Iterator;
import java.util.List;

import com.elderresearch.code.CodeFlag;

public class CopyrightHeader extends CodeFlag {
	public CopyrightHeader() { super("(c)", "Has copyright header at top of file"); }
	
	@Override
	public String compute(List<String> lines) {
		Iterator<String> iter = lines.iterator();
		if (!iter.hasNext() || !iter.next().trim().matches("/[*]+")) { return toString(false); }
		if (!iter.hasNext() || !iter.next().trim().startsWith("* Copyright (c) 20")) { return toString(false); }
		if (!iter.hasNext() || !iter.next().trim().toLowerCase().equals("* all rights reserved.")) { return toString(false); } 
		return toString(true);
	}
}
