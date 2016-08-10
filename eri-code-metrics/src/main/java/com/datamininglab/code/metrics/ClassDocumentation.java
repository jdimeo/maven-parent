/*******************************************************************************
 * Copyright (c) 2016 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.datamininglab.code.metrics;

import java.util.Iterator;
import java.util.List;

import com.datamininglab.code.CodeFlag;

public class ClassDocumentation extends CodeFlag {
	public ClassDocumentation() { super('D', "Has top-level/class comments"); }
	
	@Override
	public String compute(List<String> lines) {
		Iterator<String> iter = lines.iterator();
		String line = null;
		while (iter.hasNext()) {
			line = iter.next().trim().toLowerCase();
			if (!line.isEmpty()
			 && !line.equals("/*")
			 && !line.startsWith("*")
			 && !line.startsWith("import")
			 && !line.startsWith("package")) { break; }
		}
		return toString(line != null && line.startsWith("/**"));
	}
}
