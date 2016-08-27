/*******************************************************************************
 * Copyright (c) 2016 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.datamininglab.code;


public abstract class CodeCount implements CodeMetric<Integer> {
	private char character;
	private String name;
	protected int total;
	
	protected CodeCount(char character, String name) {
		this.character = character;
		this.name = name;
	}
	
	@Override
	public char getCharacter() { return character; }
	@Override
	public String getName() { return name; }
	@Override
	public String getFormatString() { return "%3d"; }
	
	@Override
	public String getSummary() { return String.format("%3d total", total); }
}
