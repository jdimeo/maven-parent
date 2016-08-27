/*******************************************************************************
 * Copyright (c) 2016 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.datamininglab.code;

public abstract class CodeFlag implements CodeMetric<String> {
	private static final float PCT = 100.0f;
	
	private char character;
	private String name;
	private int matched, total;
	
	protected CodeFlag(char character, String name) {
		this.character = character;
		this.name = name;
	}
	
	@Override
	public char getCharacter() { return character; }
	@Override
	public String getName() { return name; }
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
