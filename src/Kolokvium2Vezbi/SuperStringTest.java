package Kolokvium2Vezbi;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

class SuperString {
	LinkedList<String> listStrings;
	LinkedList<String> lastAdded;

	public SuperString() {
		listStrings = new LinkedList<>();
		lastAdded = new LinkedList<>();
	}

	public void append(String s) {
		listStrings.addLast(s);
		lastAdded.addLast(s);
	}

	public void insert(String s) {
		listStrings.addFirst(s);
		lastAdded.addLast(s);
	}

	public String toString() {
		return listStrings.stream()
				.reduce("", (s1, s2) -> s1 + s2);
	}

	public boolean contains(String s) {
		return toString().contains(s);
	}

	public void reverse() {
		//list = [ "st" , "arz" , "andrej: ];
		// reverse(); list = [ "jerdna", "zra", "ts"]
		Collections.reverse(listStrings);

		ListIterator<String> tmpIterator = listStrings.listIterator();
		extracted(tmpIterator);

		tmpIterator = lastAdded.listIterator();
		extracted(tmpIterator);
	}

	private static void extracted(ListIterator<String> iterator) {
		while (iterator.hasNext()) {
			iterator.set(new
					StringBuilder(iterator.next()).reverse().toString());
		}
	}

	public void removeLast(int k) {
		for (int i = 0; i < k; i++)
			listStrings.remove(lastAdded.removeLast());
	}
}

public class SuperStringTest {
	public static void main(String[] args) {
		Scanner jin = new Scanner(System.in);
		int k = jin.nextInt();
		if (k == 0) {
			SuperString s = new SuperString();
			while (true) {
				int command = jin.nextInt();
				if (command == 0) {//append(String s)
					s.append(jin.next());
				}
				if (command == 1) {//insert(String s)
					s.insert(jin.next());
				}
				if (command == 2) {//contains(String s)
					System.out.println(s.contains(jin.next()));
				}
				if (command == 3) {//reverse()
					s.reverse();
				}
				if (command == 4) {//toString()
					System.out.println(s);
				}
				if (command == 5) {//removeLast(int k)
					s.removeLast(jin.nextInt());
				}
				if (command == 6) {//end
					break;
				}
			}
		}
	}

}
