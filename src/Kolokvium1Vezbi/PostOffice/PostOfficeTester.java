package Kolokvium1Vezbi.PostOffice;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class InvalidPackageException extends Exception {

	public InvalidPackageException(String message) {
		super(message);
	}
}

abstract class Package implements Comparable<Package> {
	String name; //receiver
	String address;
	int trackingNumber;
	int weight;

	public Package(String name, String address, int trackingNumber, int weight) {
		this.name = name;
		this.address = address;
		this.trackingNumber = trackingNumber;
		this.weight = weight;
	}

	abstract double price();

	@Override
	public String toString() { //zaednickite raboti
		return String.format("%s, %s, %d, %d", name, address, trackingNumber, weight);
	}

	@Override
	public int compareTo(Package o) {
		return Double.compare(this.price(), o.price());
	}
}

class PackageFactory {
	public static Package createPackage(String line) throws InvalidPackageException {
		String[] parts = line.split("\\s+");
		//final part of string is different
		String type = parts[0];
		String name = parts[1];
		String address = parts[2];
		int trackingNumber = Integer.parseInt(parts[3]);
		int weight = Integer.parseInt(parts[4]);

		if (weight <= 0)
			throw new InvalidPackageException(line);

		//can be different
		if (type.equals("I")) {
			String region = parts[5];

			return new InternationalPackage(name, address, trackingNumber, weight, region);
		} else if (type.equals("L")) { // local
			boolean priority = Boolean.parseBoolean(parts[5]);
			//parts[5].equals("true")
			return new LocalPackage(name, address, trackingNumber, weight, priority);
		} else
			throw new InvalidPackageException(line);
	}
}

class InternationalPackage extends Package {
	String region; // mesto enum
	final static double PRICE_PER_GRAM = 1.5;

	public InternationalPackage(String name, String address, int trackingNumber, int weight, String region) {
		super(name, address, trackingNumber, weight);
		this.region = region;
	}

	@Override
	double price() {
		return weight * PRICE_PER_GRAM;
	}

	@Override
	public String toString() {
		return String.format("I, %s, %s", super.toString(), region);
	}
}

class LocalPackage extends Package {
	boolean priority;
	final static double PRICE_WITH_PRIORITY = 5.0;
	final static double REGULAR_PRICE = 3.0;

	public LocalPackage(String name, String address, int trackingNumber, int weight, boolean priority) {
		super(name, address, trackingNumber, weight);
		this.priority = priority;
	}

	@Override
	double price() {
		return priority ? PRICE_WITH_PRIORITY : REGULAR_PRICE;
	}

	@Override
	public String toString() {
		return String.format("L, %s, %s", super.toString(), priority);
	}
}

class PostOffice {
	String name;
	String city;
	List<Package> packages;

	public PostOffice(String name, String city) {
		this.name = name;
		this.city = city;
		this.packages = new ArrayList<>();
	}

	public void loadPackages(Scanner sc) throws InvalidPackageException {
		//BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			packages.add(PackageFactory.createPackage(line));
		}
		/*packages = br.lines()
				.map(line -> {
					try {
						return PackageFactory.createPackage(line);
					} catch (InvalidPackageException e) {
						System.out.println(e.getMessage());
						return null;
					}
				})
				.collect(Collectors.toList());*/
	}

	public boolean addPackage(Package p) {
		return packages.add(p);
	}

	public void printPackages(OutputStream out) {
		PrintWriter pw = new PrintWriter(out);
		packages.stream()
				.sorted(Comparator.reverseOrder())
				.forEach(pw::println);
		pw.flush();
	}

	public Package mostExpensive() {
		return packages.stream().max(Comparator.naturalOrder()).get();
	}
}

public class PostOfficeTester {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PostOffice office = new PostOffice("Poshta", "Skopje");

		try {
			office.loadPackages(sc);
			office.printPackages(System.out);
			System.out.println();
			System.out.println(office.mostExpensive());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
