package Kolokvium2Vezbi;

import java.util.*;
import java.util.stream.IntStream;

class IntegerList {
	LinkedList<Integer> listIntegers;

	public IntegerList() {
		listIntegers = new LinkedList<Integer>();
	}

	public IntegerList(Integer... numbers) {
		this();
		listIntegers.addAll(Arrays.asList(numbers));
	}

	public void add(int element, int index) {
		while (index > listIntegers.size()) {
			listIntegers.add(0);
		}
		listIntegers.add(index, element);
	}

	public int remove(int index) {
		return listIntegers.remove(index);
	}

	public void set(int element, int index) {
		listIntegers.set(index, element);
	}

	public int get(int index) {
		return listIntegers.get(index);
	}

	public int size() {
		return listIntegers.size();
	}

	public int count(int element) {
		return (int) listIntegers.stream()
				.filter(i -> i == element)
				.count();
	}

	public void removeDuplicates() {
		TreeSet<Integer> toRemove = new TreeSet<>();
		for (Iterator<Integer> it = listIntegers.descendingIterator();
		     it.hasNext(); ) {
			int k = it.next();
			if (toRemove.contains(k)) it.remove();
			else if (count(k) >= 2) toRemove.add(k);
		}
	}

	public int sumFirst(int k) {
		int sum = 0;
		for (Iterator<Integer> iterator = listIntegers.iterator();
		     iterator.hasNext() && k > 0; --k) {
			sum += iterator.next();
		}
		return sum;
	}

	public int sumLast(int k) {
		int sum = 0;
		for (Iterator<Integer> it = listIntegers.descendingIterator();
		     it.hasNext() && k > 0; --k) {
			sum += it.next();
		}
		return sum;
	}

	public IntegerList addValue(int value) {
		IntegerList result = new IntegerList();
		int counter = 0;
		for (Iterator<Integer> it = listIntegers.iterator();
		     it.hasNext(); ++counter) {
			result.add(it.next() + value, counter);
		}
		return result;
	}

	private void shift(int index, int k) {
		int position = ((index + k) % listIntegers.size() + listIntegers.size()) % listIntegers.size();
		add(remove(index), position);
	}

	public void shiftRight(int index, int k) {
		shift(index, k);
	}

	public void shiftLeft(int index, int k) {
		shift(index, -k);
	}

}

public class IntegerListTest {

	public static void main(String[] args) {
		Scanner jin = new Scanner(System.in);
		int k = jin.nextInt();
		if (k == 0) { //test standard methods
			int subtest = jin.nextInt();
			if (subtest == 0) {
				IntegerList list = new IntegerList();
				while (true) {
					int num = jin.nextInt();
					if (num == 0) {
						list.add(jin.nextInt(), jin.nextInt());
					}
					if (num == 1) {
						list.remove(jin.nextInt());
					}
					if (num == 2) {
						print(list);
					}
					if (num == 3) {
						break;
					}
				}
			}
			if (subtest == 1) {
				int n = jin.nextInt();
				Integer a[] = new Integer[n];
				for (int i = 0; i < n; ++i) {
					a[i] = jin.nextInt();
				}
				IntegerList list = new IntegerList(a);
				print(list);
			}
		}
		if (k == 1) { //test count,remove duplicates, addValue
			int n = jin.nextInt();
			Integer a[] = new Integer[n];
			for (int i = 0; i < n; ++i) {
				a[i] = jin.nextInt();
			}
			IntegerList list = new IntegerList(a);
			while (true) {
				int num = jin.nextInt();
				if (num == 0) { //count
					System.out.println(list.count(jin.nextInt()));
				}
				if (num == 1) {
					list.removeDuplicates();
				}
				if (num == 2) {
					print(list.addValue(jin.nextInt()));
				}
				if (num == 3) {
					list.add(jin.nextInt(), jin.nextInt());
				}
				if (num == 4) {
					print(list);
				}
				if (num == 5) {
					break;
				}
			}
		}
		if (k == 2) { //test shiftRight, shiftLeft, sumFirst , sumLast
			int n = jin.nextInt();
			Integer a[] = new Integer[n];
			for (int i = 0; i < n; ++i) {
				a[i] = jin.nextInt();
			}
			IntegerList list = new IntegerList(a);
			while (true) {
				int num = jin.nextInt();
				if (num == 0) { //count
					list.shiftLeft(jin.nextInt(), jin.nextInt());
				}
				if (num == 1) {
					list.shiftRight(jin.nextInt(), jin.nextInt());
				}
				if (num == 2) {
					System.out.println(list.sumFirst(jin.nextInt()));
				}
				if (num == 3) {
					System.out.println(list.sumLast(jin.nextInt()));
				}
				if (num == 4) {
					print(list);
				}
				if (num == 5) {
					break;
				}
			}
		}
	}

	public static void print(IntegerList il) {
		if (il.size() == 0) System.out.print("EMPTY");
		for (int i = 0; i < il.size(); ++i) {
			if (i > 0) System.out.print(" ");
			System.out.print(il.get(i));
		}
		System.out.println();
	}

}
