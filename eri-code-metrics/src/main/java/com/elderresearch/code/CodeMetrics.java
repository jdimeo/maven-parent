/*******************************************************************************
 * Copyright (c) 2016 Elder Research, Inc.
 * All rights reserved.
 *******************************************************************************/
package com.elderresearch.code;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.elderresearch.code.metrics.Authorship;
import com.elderresearch.code.metrics.ClassDocumentation;
import com.elderresearch.code.metrics.CopyrightHeader;
import com.elderresearch.code.metrics.Creation;
import com.elderresearch.code.metrics.UnitTests;

/**
 * Outputs ERI code metrics.
 * 
 * @author <a href="mailto:dimeo@datamininglab.com">John Dimeo</a>
 * @since Jun 5, 2015
 */
public class CodeMetrics extends SimpleFileVisitor<Path> {
	private static final int METRIC_NAME_MAX_LEN = 40;
	
	@Parameter(names = "-root", description = "The root directory to scan")
	private String root = ".";
	
	@Parameter(names = "-ext", description = "One or more extensions to include (defaults to .java and .scala)")
	private List<String> extensions = new LinkedList<>();
	
	@Parameter(names = "-skip", description = "Directories to skip, for example folders with auto-generated source")
	private List<String> exclusions = new LinkedList<>();
	
	private boolean measureFileNameLength;
	private int maxFileNameLength;
	
	private CodeMetric<?>[] metrics;
	private String metricsFmt;
	
	private PrintStream out = System.out;
	
	public static void main(String[] args) {
		CodeMetrics cm = new CodeMetrics();
		JCommander jc = new JCommander(cm);
		try {
			jc.parse(args);
			cm.run();
		} catch (ParameterException e) {
			jc.usage();
		}
	}
	
	public void setRoot(String root) { this.root = root; }
	
	public void setMetrics(CodeMetric<?>... metrics) {
		this.metrics = metrics;

		StringBuilder sb = new StringBuilder();
		for (CodeMetric<?> cm : metrics) { sb.append("|").append(cm.getFormatString()); }
		metricsFmt = sb.toString();
	}
	
	public void setOut(PrintStream out) { this.out = out; }
		
	public void run() {
		if (extensions.isEmpty()) {
			extensions.add(".java");
			extensions.add(".scala");
		}
		
		if (exclusions.isEmpty()) {
			exclusions.add("target");
		}
		
		if (metrics == null) {
			setMetrics(new CopyrightHeader(), new ClassDocumentation(), new Authorship(), new Creation(), new UnitTests());
		}
		
		Path start = Paths.get(root);
		root = start.toString();
		
		try {
			measureFileNameLength = true;
			Files.walkFileTree(start, this);
			metricsFmt = "%-" + maxFileNameLength + "s" + metricsFmt;
			
			out.print(String.format("%-" + maxFileNameLength + "s", "File:"));
			for (CodeMetric<?> cm : metrics) { out.print("|" + cm.getCode()); }
			out.println();
			
			measureFileNameLength = false;
			Files.walkFileTree(start, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		out.println(System.lineSeparator() + "Summary:");
		for (CodeMetric<?> cm : metrics) {
			out.print(cm.getCode() + " " + cm.getName());
			for (int i = 0; i < METRIC_NAME_MAX_LEN - cm.getName().length(); i++) { out.print("."); }
			out.println(cm.getSummary());
		}
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		for (String exclusion : exclusions) {
			if (dir.toString().endsWith(exclusion)) { return FileVisitResult.SKIP_SUBTREE; }
		}
		return super.preVisitDirectory(dir, attrs);
	}
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		String fileStr = file.toString();
		for (String exclusion : exclusions) {
			if (fileStr.endsWith(exclusion)) { return FileVisitResult.CONTINUE; }
		}
		
		String match = null;
		for (String ext : extensions) {
			if (fileStr.endsWith(ext)) {
				match = ext;
				break;
			}
		}
		if (match == null) {
			return FileVisitResult.CONTINUE;
		}
		
		fileStr = fileStr.substring(root.length() + 1, fileStr.length() - match.length());
		
		if (measureFileNameLength) {
			maxFileNameLength = Math.max(fileStr.length(), maxFileNameLength);
			return FileVisitResult.CONTINUE;
		}
		
		List<String> lines = new LinkedList<>();
		try (Scanner s = new Scanner(file.toFile())) {
			while (s.hasNextLine()) { lines.add(s.nextLine()); }
		}
		
		Object[] vals = new Object[metrics.length + 1];
		vals[0] = fileStr;
		for (int i = 0; i < metrics.length; i++) {
			vals[i + 1] = metrics[i].compute(lines);
		}
		
		out.println(String.format(metricsFmt, vals));
		return FileVisitResult.CONTINUE;
	}
}
