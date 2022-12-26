package Auditoriski10.PhoneBook;

import java.util.*;

class DuplicateNumberException extends Exception {
	DuplicateNumberException(String number) {
		super(String.format("Duplicate number: %s", number));
	}
}

class Contact {
	String name;
	String number;
	static Comparator<Contact> COMPARATOR = Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber);

	public Contact(String name, String number) {
		this.name = name;
		this.number = number;
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
	//Ne smee duplikati telefonski broevi
	Set<String> uniqueNumbers;
	private Map<String, Set<Contact>> contactsByName; //TreeSet for sorted

	PhoneBook() {
		uniqueNumbers = new HashSet<>(); //O(1) access to number
		contactsByName = new HashMap<>();
	}

	public void addContact(String name, String number) throws DuplicateNumberException {
		if (uniqueNumbers.contains(number))
			throw new DuplicateNumberException(number);

		uniqueNumbers.add(number);

		contactsByName.putIfAbsent(name, new TreeSet<>(Contact.COMPARATOR));
		contactsByName.get(name).add(new Contact(name, number));
	}

	public void contactsByName(String name) {
		if (!contactsByName.containsKey(name)) {
			System.out.println("NOT FOUND");
		} else {
			contactsByName.get(name).forEach(System.out::println);
		}
	}
}

public class PhoneBookTest {
	public static void main(String[] args) {
		//given code
	}
}
