package Kolokvium2Vezbi;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class UserAlreadyExistsException extends Exception {
	UserAlreadyExistsException(String message) {
		super(message);
	}
}

class User {
	String email;
	String name;
	String phoneNumber;
	int id;
	static int ID_COUNTER = 1;

	public User(String email, String name, String phoneNumber) {
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		id = ID_COUNTER++;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getId() {
		return id;
	}
}

class PizzaApp {
	Map<String, User> userByEmail;
	Map<String, Float> revenueByPizza;

	//pizzaName -> {email -> orders}
	Map<String, Map<String, Integer>> frequenciesByUserAndPizza;

	public PizzaApp() {
		userByEmail = new HashMap<>();
		revenueByPizza = new TreeMap<>();
		frequenciesByUserAndPizza = new TreeMap<>();
	}

	void registerUser(String email, String name, String phoneNumber) throws UserAlreadyExistsException {
		if (userByEmail.containsKey(email))
			throw new UserAlreadyExistsException(String.format("User with email %s already exists", email));

		User user = new User(email, name, phoneNumber);
		userByEmail.put(email, user);
	}

	void makeOrder(String email, String pizzaName, float pizzaPrice) {
		revenueByPizza.putIfAbsent(pizzaName, 0.0f); // vkupno pari
		//revenueByPizza.put(pizzaName, revenueByPizza.get(pizzaName) + pizzaPrice);

		//za pizza so ime pizzaName, dodadi revenue do sega + od nova naracka
		revenueByPizza.computeIfPresent(pizzaName, (k, v) -> {
			v += pizzaPrice;
			return v;
		});

		//Initialize values
		frequenciesByUserAndPizza.putIfAbsent(pizzaName, new HashMap<>());
		frequenciesByUserAndPizza.get(pizzaName).putIfAbsent(email, 0); //pocetno 0 naracki

		//Za dadena pizza, zemi korisnik i zgolemi negovi naracki
		frequenciesByUserAndPizza.get(pizzaName)
				.computeIfPresent(email, (user, orders) -> ++orders);
	}

	void printRevenueByPizza() {
//		revenueByPizza.forEach((k, v) -> System.out.printf("%s %.2f \n", k, v));
		revenueByPizza.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEach(entry -> System.out.println(String.format("%s %2f", entry.getKey(), entry.getValue())));
	}

	void printMostFrequentUserForPizza() {
		//za sekoja pica naj frekventen korisnik
		for (Map.Entry<String, Map<String, Integer>> entry : frequenciesByUserAndPizza.entrySet()) {
			System.out.println(String.format("Pizza: %s", entry.getKey()));

			Map<String, Integer> frequencyByEmail = entry.getValue();
			int maxFrequency = frequencyByEmail.values().stream()
					.mapToInt(i -> i).max().getAsInt();

			frequencyByEmail.keySet().stream() //get all keys
					.filter(email -> frequencyByEmail.get(email) == maxFrequency)//filter only those with max frequency
					.map(email -> userByEmail.get(email)) // get those users' emails
					.sorted(Comparator.comparing(User::getId)) // sort by IDs (if more)
					.forEach(user -> {
						System.out.println(String.format("%d %s %d",
								user.getId(), user.getEmail(), maxFrequency));
					});
		}
	}
}


public class PizzaAppTest {

}
