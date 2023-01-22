package Kolokvium2Vezbi;

import java.util.*;

class Candidate {
	String code;
	String name;
	int age;

	public Candidate(String code, String name, int age) {
		this.code = code;
		this.name = name;
		this.age = age;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return String.format("%s %s %d", code, name, age);
	}
}

class Audition {
	//City -> (Code, Candidate)
	Map<String, Map<String, Candidate>> candidatesByCity;

	public Audition() {
		candidatesByCity = new HashMap<>();
	}

	public void addParticipant(String city, String code, String name, int age) {
		candidatesByCity.putIfAbsent(city, new HashMap<>());
		Map<String, Candidate> fromCity = candidatesByCity.get(city);
		fromCity.putIfAbsent(code, new Candidate(code, name, age));
		candidatesByCity.putIfAbsent(city, fromCity);
	}

	public void listByCity(String city) {
		Map<String, Candidate> candidatesFromCity = candidatesByCity.get(city);
		candidatesFromCity.values().stream()
				.sorted(Comparator.comparing(Candidate::getName)
						.thenComparing(Candidate::getAge))
				.forEach(System.out::println);
	}
}

public class AuditionTest {
	public static void main(String[] args) {
		Audition audition = new Audition();
		List<String> cities = new ArrayList<String>();
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] parts = line.split(";");
			if (parts.length > 1) {
				audition.addParticipant(parts[0], parts[1], parts[2],
						Integer.parseInt(parts[3]));
			} else {
				cities.add(line);
			}
		}
		for (String city : cities) {
			System.out.printf("+++++ %s +++++\n", city);
			audition.listByCity(city);
		}
		scanner.close();
	}
}
