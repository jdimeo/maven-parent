/*
 * Copyright (c) 2015 Elder Research, Inc.
 * All rights reserved.
 */
package com.datamininglab.code.metrics;

import java.util.Iterator;
import java.util.List;

import com.datamininglab.code.CodeFlag;

public class CopyrightHeader extends CodeFlag {
	public CopyrightHeader() { super('©', "Has copyright header at top of file"); }
	
	@Override
	public String compute(List<String> lines) {
		Iterator<String> iter = lines.iterator();
		if (!iter.hasNext() || !iter.next().trim().equals("/*")) { return toString(false); }
		if (!iter.hasNext() || !iter.next().trim().startsWith("* Copyright (c) ")) { return toString(false); }
		if (!iter.hasNext() || !iter.next().trim().toLowerCase().equals("* all rights reserved.")) { return toString(false); } 
		return toString(true);
	}
}
