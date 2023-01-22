package Kolokvium2Vezbi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

class TermFrequency {
	Map<String, Integer> countByWords;
	Set<String> ignoreWords;

	public TermFrequency(InputStream in, String[] stopWords) {
		countByWords = new TreeMap<>();
		ignoreWords = new HashSet<>();
		ignoreWords.addAll(Arrays.asList(stopWords));


		Scanner sc = new Scanner(in);
		while (sc.hasNext()) {
			String line = sc.nextLine();
			line = line.trim();

			if (line.length() > 0) {
				String[] words = line.split("\\s+");

				for (String word : words) {
					String key = normalize(word);
					if (key.isEmpty() || ignoreWords.contains(key))
						continue;

					if (countByWords.containsKey(key)) {
						int count = countByWords.get(key);
						countByWords.put(key, count + 1);
					} else {
						countByWords.put(key, 1);
					}
				}
			}
		}
		sc.close();
	}

	private static String normalize(String word) {
		return word.toLowerCase()
				.replace(",", "")
				.replace(".", "")
				.trim();
	}

	public int countTotal() {
		int total = 0;
		for (Integer amount : countByWords.values())
			total += amount;
		return total;
	}

	public int countDistinct() {
		return countByWords.keySet().size();
	}

	public List<String> mostOften(int k) {
		List<String> result = new ArrayList<>();

		SortedSet<Map.Entry<String, Integer>> sorted = entriesSortedByValues(countByWords);

		for (Entry<String, Integer> entry : sorted) {
			result.add(entry.getKey());
			--k;
			if (k == 0) break;
		}
		return result;
	}

	private static <K, V extends Comparable<? super V>>
	SortedSet<Map.Entry<K, V>>
	entriesSortedByValues(Map<K, V> map) {
		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
				new Comparator<Map.Entry<K, V>>() {
					@Override
					public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
						int res = o1.getValue().compareTo(o2.getValue());
						return res != 0 ? -res : 1;
					}
				});
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}
}

public class TermFrequencyTest {
	public static void main(String[] args) throws FileNotFoundException {
		String[] stop = new String[]{"во", "и", "се", "за", "ќе", "да", "од",
				"ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
				"што", "на", "а", "но", "кој", "ја"};
		TermFrequency tf = new TermFrequency(System.in,
				stop);
		System.out.println(tf.countTotal());
		System.out.println(tf.countDistinct());
		System.out.println(tf.mostOften(10));
	}
}
// vasiot kod ovde

