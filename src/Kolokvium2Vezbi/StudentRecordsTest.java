package Kolokvium2Vezbi;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


class StudentX {
	String code;
	String major;
	List<Integer> grades;
	float average;

	public StudentX(String code, String major, List<Integer> grades) {
		this.code = code;
		this.major = major;
		this.grades = grades;
		average = (float) grades.stream().mapToInt(i -> i).average().orElse(5);
	}

	public String getCode() {
		return code;
	}

	public String getMajor() {
		return major;
	}

	public float getAverage() {
		return average;
	}

	@Override
	public String toString() {
		return String.format("%s %.2f", code, average);
	}
}

class StudentFactory {

	public static StudentX createStudent(String line) {
		String[] parts = line.split("\\s+");
		String code = parts[0];
		String major = parts[1];
		List<Integer> grades = new ArrayList<>();
		for (int i = 2; i < parts.length; i++) {
			grades.add(Integer.parseInt(parts[i]));
		}

		return new StudentX(code, major, grades);
	}

}

class StudentRecords {
	Map<String, List<StudentX>> studentsByMajor;
	//Major -> (Grade -> Number of said grade)
	Map<String, Map<Integer, Integer>> majorsByGrades;

	public StudentRecords() {
		studentsByMajor = new HashMap<>();
		majorsByGrades = new HashMap<>();
	}

	int readRecords(InputStream in) {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		List<StudentX> students = br.lines()
				.map(StudentFactory::createStudent)
				.collect(Collectors.toList());

		for (StudentX student : students) {
			studentsByMajor.putIfAbsent(student.major, new ArrayList<>());
			studentsByMajor.get(student.major).add(student);

			majorsByGrades.putIfAbsent(student.major, new HashMap<>());
			for (Integer grade : student.grades) {
				majorsByGrades.get(student.major).putIfAbsent(grade, 0);
				majorsByGrades.get(student.major).computeIfPresent(grade, (k, num) -> ++num);
			}

		}

		return students.size();
	}

	void writeTable(OutputStream os) {
		PrintWriter pw = new PrintWriter(os);

		for (String major : studentsByMajor.keySet()) {
			pw.println(major);
			studentsByMajor.get(major).stream()
					.sorted(Comparator.comparing(StudentX::getAverage).reversed()
							.thenComparing(StudentX::getCode))
					.forEach(pw::println);
		}

		pw.flush();
	}

	void writeDistribution(OutputStream os) {
		PrintWriter pw = new PrintWriter(os);
		List<Map<Integer, Integer>> collect = majorsByGrades.values().stream()
				.sorted(Comparator.comparing(i -> i.get(10)))
				.collect(Collectors.toList());


		Collections.reverse(collect);

		for (Map<Integer, Integer> map : collect) {

			for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				pw.printf("%2d | ", entry.getKey());

				for (int i = 0; i < entry.getValue(); i += 10) {
					pw.print("*");
				}

				pw.printf("(%d)\n", entry.getValue());
			}
		}
		pw.flush();
	}
}

/**
 * January 2016 Exam problem 1
 */
public class StudentRecordsTest {
	public static void main(String[] args) {
		System.out.println("=== READING RECORDS ===");
		StudentRecords studentRecords = new StudentRecords();
		int total = studentRecords.readRecords(System.in);
		System.out.printf("Total records: %d\n", total);
		System.out.println("=== WRITING TABLE ===");
		studentRecords.writeTable(System.out);
		System.out.println("=== WRITING DISTRIBUTION ===");
		studentRecords.writeDistribution(System.out);
	}
}
