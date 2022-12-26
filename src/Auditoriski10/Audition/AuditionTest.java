package Auditoriski10.Audition;

import java.util.*;

class Candidate {
	String city;
	String code;
	String name;
	int age;

	public Candidate(String city, String code, String name, int age) {
		this.city = city;
		this.code = code;
		this.name = name;
		this.age = age;
	}

	public String getCity() {
		return city;
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
		//003 Аце 17
		return String.format("%s %s %d", code, name, age);
	}
}

class Audition {
	//K = city, V = Map( K = code, V = Candidate)
	Map<String, Map<String, Candidate>> participantsByCity;

	public Audition() {
		this.participantsByCity = new HashMap<>(); //O(1) complexity
	}

	public void addParticipant(String city, String code, String name, int age) {
		Candidate candidate = new Candidate(city, code, name, age);

//		//First by city
//		if (!participantsByCity.containsKey(city)) {
//			participantsByCity.put(city, new HashMap<>());
//		}
//
//		//Now inside the city map
//		Map<String, Candidate> participantsInCity = participantsByCity.get(city);
//
//		//If candidate with code is in city, skip
//		if(!participantsInCity.containsKey(code)) {
//			participantsInCity.put(code, candidate);
//		}
//		//Put city if absent
		participantsByCity.putIfAbsent(code, new HashMap<>());
		Map<String, Candidate> participantsInTheCity = participantsByCity.get(city);
		participantsInTheCity.putIfAbsent(code, candidate);
	}

	public void listByCity(String city) {
		Map<String, Candidate> participantsInTheCity = participantsByCity.get(city);
		//name, if isto spored vozrast
		Comparator<Candidate> comparator = Comparator.comparing(Candidate::getName).thenComparing(Candidate::getAge);
		participantsInTheCity.values()
				.stream()
				.sorted(comparator)
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