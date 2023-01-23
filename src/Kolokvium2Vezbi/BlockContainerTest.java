package Kolokvium2Vezbi;

import java.util.*;
import java.util.stream.Collectors;

class BlockContainer<T> implements Comparable<T> {
	List<Set<T>> elements;
	int n;

	public BlockContainer(int n) {
		this.n = n;
		elements = new ArrayList<Set<T>>();
	}

	public void add(T element) {
		if (elements.size() == 0) {
			Set<T> set = new TreeSet<>();
			set.add(element);
			elements.add(set);
		}
		//Size of last set in list
		else if (elements.get(elements.size() - 1).size() == n) {
			Set<T> set = new TreeSet<>();
			set.add(element);
			elements.add(set);
		} else {
			elements.get(elements.size() - 1).add(element);
		}
	}

	public boolean remove(T a) {
		boolean flag;
		int size = elements.size() - 1;

		flag = elements.get(size).remove(a);
		if (elements.get(size).size() == 0) {
			elements.remove(size);
		}

		return flag;
	}

	public void sort() {
		List<T> allElements = elements.stream()
				.flatMap(Collection::stream)
				.sorted()
				.collect(Collectors.toList());

		elements.clear();

		for (T element : allElements) {
			add(element);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < elements.size() - 1; i++) {
			sb.append(elements.get(i) + ",");
		}
		sb.append(elements.get(elements.size() - 1));
		return sb.toString();
	}

	@Override
	public int compareTo(T o) {
		return 0;
	}
}

public class BlockContainerTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int size = scanner.nextInt();
		BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
		scanner.nextLine();
		Integer lastInteger = null;
		for (int i = 0; i < n; ++i) {
			int element = scanner.nextInt();
			lastInteger = element;
			integerBC.add(element);
		}
		System.out.println("+++++ Integer Block Container +++++");
		System.out.println(integerBC);
		System.out.println("+++++ Removing element +++++");
		integerBC.remove(lastInteger);
		System.out.println("+++++ Sorting container +++++");
		integerBC.sort();
		System.out.println(integerBC);
		BlockContainer<String> stringBC = new BlockContainer<String>(size);
		String lastString = null;
		for (int i = 0; i < n; ++i) {
			String element = scanner.next();
			lastString = element;
			stringBC.add(element);
		}
		System.out.println("+++++ String Block Container +++++");
		System.out.println(stringBC);
		System.out.println("+++++ Removing element +++++");
		stringBC.remove(lastString);
		System.out.println("+++++ Sorting container +++++");
		stringBC.sort();
		System.out.println(stringBC);
	}
}

// Вашиот код овде



