package Kolokvium2Vezbi;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class DuplicateNumberException extends Exception {
	DuplicateNumberException(String number) {
		super(String.format("Duplicate number: %s", number));
	}
}

class Contact {
	String name;
	String number;

	public Contact(String name, String number) {
		this.name = name;
		this.number = number;
	}

	public boolean hasNumber(String num) {
		return number.contains(num);
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return String.format("%s %s", name, number);
	}
}

class PhoneBook {
	// Number -> Contact
	Map<String, Contact> contactsByNumber;
	Map<String, List<Contact>> contactsByName;

	public PhoneBook() {
		contactsByNumber = new HashMap<>();
		contactsByName = new HashMap<>();
	}

	void addContact(String name, String number) throws DuplicateNumberException {
		if (contactsByNumber.containsKey(number))
			throw new DuplicateNumberException(number);
		Contact contact = new Contact(name, number);

		contactsByNumber.putIfAbsent(number, contact);

		contactsByName.putIfAbsent(name, new ArrayList<>());
		contactsByName.get(name).add(contact);

	}

	void contactsByNumber(String number) {
		List<Contact> result = contactsByNumber.values().stream()
				.filter(i -> i.hasNumber(number))
				.sorted(Comparator.comparing(Contact::getName)
						.thenComparing(Contact::getNumber))
				.collect(Collectors.toList());
		if (result.size() == 0) System.out.println("NOT FOUND");
		else result.forEach(System.out::println);
	}

	void contactsByName(String name) {
		List<Contact> filtered = contactsByName.values().stream()
				.flatMap(Collection::stream)
				.filter(i -> i.getName().equalsIgnoreCase(name))
				.sorted(Comparator.comparing(Contact::getName)
						.thenComparing(Contact::getNumber))
				.collect(Collectors.toList());
		if (filtered.isEmpty()) System.out.println("NOT FOUND");
		else filtered.forEach(System.out::println);
	}
}

public class PhoneBookTest {

	public static void main(String[] args) {
		PhoneBook phoneBook = new PhoneBook();
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < n; ++i) {
			String line = scanner.nextLine();
			String[] parts = line.split(":");
			try {
				phoneBook.addContact(parts[0], parts[1]);
			} catch (DuplicateNumberException e) {
				System.out.println(e.getMessage());
			}
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			System.out.println(line);
			String[] parts = line.split(":");
			if (parts[0].equals("NUM")) {
				phoneBook.contactsByNumber(parts[1]);
			} else {
				phoneBook.contactsByName(parts[1]);
			}
		}
	}

}

