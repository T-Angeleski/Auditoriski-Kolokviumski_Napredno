package Kolokvium2Vezbi;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Row {
	List<Integer> numbers;

	public Row(String line) {
		numbers = Arrays.stream(line.split("\\s+"))
				.map(Integer::parseInt)
				.collect(Collectors.toList());
	}

	public int maxNum() {
		return numbers.stream().mapToInt(i -> i).max().getAsInt();
	}

	public boolean condition() {
		//max number with max frequency
		int max = numbers.stream().mapToInt(i -> i).max().getAsInt();
		//po sto kje grupirame, sto kje bide rezultantna vrednost
		Map<Integer, Long> countingMap = numbers.stream()
				.collect(Collectors.groupingBy(
						i -> i,
						Collectors.counting()
				));

		int frequencyOfMaxNumber = countingMap.get(max).intValue();
		int maxFrequency = countingMap.values().stream()
				.mapToInt(Long::intValue)
				.max()
				.getAsInt();

		return frequencyOfMaxNumber == maxFrequency;
	}
}

class NumberProcessor {
	List<Row> rows;

	public NumberProcessor() {
		rows = new ArrayList<>();
	}

	void readRows(InputStream in) {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		rows = br.lines().map(Row::new).collect(Collectors.toList());
	}

	void printMaxFromRows(OutputStream os) {
		PrintWriter pw = new PrintWriter(os);

		//samo najgolem broj od red so koja isto ima najgolema frekvencija
		rows.stream()
				.filter(Row::condition)
				.map(Row::maxNum)
				.forEach(pw::println);
		pw.flush();
	}
}

public class NumbersProcessorTest {
	public static void main(String[] args) {
		NumberProcessor numberProcessor = new NumberProcessor();
		numberProcessor.readRows(System.in);
		numberProcessor.printMaxFromRows(System.out);
	}
}
