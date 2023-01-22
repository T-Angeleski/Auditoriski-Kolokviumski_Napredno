package Kolokvium2Vezbi;

import java.util.*;
import java.util.stream.Collectors;

class Names {
	Map<String, Integer> occurrencesByName;

	public Names() {
		occurrencesByName = new TreeMap<>();
	}

	public void addName(String name) {
		occurrencesByName.putIfAbsent(name, 0);
		occurrencesByName.computeIfPresent(name, (n, o) -> ++o);
	}

	private int countUnique(String name) {
		Set<Character> uniqueChars = new HashSet<>();
		for (char c : name.toLowerCase().toCharArray()) {
			uniqueChars.add(c);
		}
		return uniqueChars.size();
	}

	public void printN(int n) {
		occurrencesByName.entrySet().stream()
				.filter(i -> i.getValue() >= n)
				.forEach(name -> System.out.printf("%s (%d) %d\n",
						name.getKey(),
						name.getValue(),
						countUnique(name.getKey())));
	}

	public String findName(int len, int x) {
		List<String> result = occurrencesByName.keySet().stream()
				.filter(name -> name.length() < len)
				.collect(Collectors.toList());
		return result.get(x % (result.size()));
	}
}

public class NamesTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		scanner.nextLine();
		Names names = new Names();
		for (int i = 0; i < n; ++i) {
			String name = scanner.nextLine();
			names.addName(name);
		}
		n = scanner.nextInt();
		System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
		names.printN(n);
		System.out.println("===== FIND NAME =====");
		int len = scanner.nextInt();
		int index = scanner.nextInt();
		System.out.println(names.findName(len, index));
		scanner.close();

	}
}

