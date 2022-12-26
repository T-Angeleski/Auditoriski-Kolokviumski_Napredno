package Auditoriski10.Names;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class NamesTest {

	private static Map<String, Integer> readNames(InputStream in) {
		Map<String, Integer> result = new HashMap<>(); // ne ni e vazen redosled nitu sortiranje

		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		br.lines().forEach(line -> {
			String[] parts = line.split("\\s+");
			String name = parts[0];
			int frequency = Integer.parseInt(parts[1]);

			result.put(name, frequency);
		});

		return result;
	}

	public static void main(String[] args) {
		//Read from 2 txt files Boy and Girl Names
		//Find how many of those are in both lists
		//Print their frequency as well

		Map<String, Integer> boyNames = readNames(System.in); // treba od file
		Map<String, Integer> girlNames = readNames(System.in);

		// so druga mapa, +1 ako se pojavi vo boy/girl names, =2 go ima na dvete mesta

		//drug nacin
		Set<String> allNames = new HashSet<>();
		allNames.addAll(boyNames.keySet());
		allNames.addAll(girlNames.keySet());

		//Baranje -> soritraj spored frekvencija
		Map<String, Integer> unisexNames = new TreeMap<>();

		allNames.stream()
				.filter(name -> boyNames.containsKey(name) && girlNames.containsKey(name))
				.forEach(name -> System.out.printf("%s -> %d\n", name, boyNames.get(name) + girlNames.get(name)));
//				.forEach(name -> unisexNames.put(name, boyNames.get(name) + girlNames.get(name))); // INCORRECT

		//Map -> one single object of K,V
		//EntrySet -> Mnozestvo objekti od K,V
		unisexNames.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEach(entry -> System.out.printf("%s -> %d\n", entry, entry.getValue()));
//		unisexNames.forEach((k, v) -> System.out.println(String.format("%s -> %d", k ,v)));
	}
}
