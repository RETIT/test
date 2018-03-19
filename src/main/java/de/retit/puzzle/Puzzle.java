package de.retit.puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.retit.puzzle.components.CsvReader;
import de.retit.puzzle.components.ResultAggregator;
import de.retit.puzzle.components.ResultWriter;
import de.retit.puzzle.entity.Measurement;
import de.retit.puzzle.util.MultiThreadingUtil;

public class Puzzle {

	private static final int THREAD_COUNT = 4;

	private static long previousTime;

	private String inputFile;
	private String outputDirectory;

	public Puzzle(String inputFile, String outputDirectory) {
		this.inputFile = inputFile;
		this.outputDirectory = outputDirectory;
	}

	public void start() throws InterruptedException {
		System.out.println();
		previousTime = System.nanoTime();

		// Read input CSV data
		List<String> csv = new CsvReader(inputFile).read();
		printTimeForTask("CsvReader");

		// Aggregate Result
		ResultAggregator aggregator = new ResultAggregator(csv);
		aggregator.aggregate();
		Map<String, List<Measurement>> aggregatedResult = aggregator.getResult();
		printTimeForTask("ResultAggregator");

		// Write result to disk
		new ResultWriter(outputDirectory, aggregatedResult).write();
		printTimeForTask("ResultWriter");
	}

	private static void printTimeForTask(String task) {
		long currentTime = System.nanoTime();
		if (previousTime != 0) {
			double passedTime = (currentTime - previousTime) / 1000000000.0;
			System.out.println(task + ": " + passedTime + "s");
		}
		previousTime = currentTime;
	}
}
