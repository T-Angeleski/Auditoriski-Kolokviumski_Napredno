package Kolokvium1Vezbi.F1Trka;


import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class Lap implements Comparable<Lap> {
	int minutes;
	int seconds;
	int milliseconds;

	public Lap(int minutes, int seconds, int milliseconds) {
		this.minutes = minutes;
		this.seconds = seconds;
		this.milliseconds = milliseconds;
	}

	public int convertToMS() {
		return milliseconds + seconds * 1000 + minutes * 60 * 1000;
	}

	@Override
	public int compareTo(Lap o) {
		return Integer.compare(this.convertToMS(), o.convertToMS());
	}

	@Override
	public String toString() {
		return String.format("%d:%02d:%03d", minutes, seconds, milliseconds);
	}
}

class DriverFactory {

	private static Lap createLap(String line) {
		//1:55:523
		String[] parts = line.split(":");
		int min = Integer.parseInt(parts[0]);
		int sec = Integer.parseInt(parts[1]);
		int ms = Integer.parseInt(parts[2]);
		return new Lap(min, sec, ms);
	}

	public static Driver createDriver(String line) {
		//Vetel 1:55:523 1:54:987 1:56:134
		String[] parts = line.split("\\s+");
		String name = parts[0];
		Lap lap1 = createLap(parts[1]);
		Lap lap2 = createLap(parts[2]);
		Lap lap3 = createLap(parts[3]);

		return new Driver(name, lap1, lap2, lap3);
	}
}


class Driver implements Comparable<Driver> {
	String name;
	Lap lap1;
	Lap lap2;
	Lap lap3;

	public Driver(String name, Lap lap1, Lap lap2, Lap lap3) {
		this.name = name;
		this.lap1 = lap1;
		this.lap2 = lap2;
		this.lap3 = lap3;
	}

	public Lap findFastestLap() {
		int l1Time = lap1.convertToMS();
		int l2Time = lap2.convertToMS();
		int l3Time = lap3.convertToMS();

		int min = Math.min(Math.min(l1Time, l2Time), l3Time);

		if (min == l1Time) return lap1;
		if (min == l2Time) return lap2;
		return lap3;
	}

	@Override
	public int compareTo(Driver o) {
		return this.findFastestLap().compareTo(o.findFastestLap());
	}

	@Override
	public String toString() {
		return String.format("%-10s%10s", name, findFastestLap());
	}
}

class F1Race {
	List<Driver> drivers;

	public F1Race(List<Driver> drivers) {
		this.drivers = drivers;
	}

	public F1Race() {
		this.drivers = new ArrayList<>();
	}

	public void readResults(InputStream in) {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		this.drivers = br.lines()
				.map(DriverFactory::createDriver)
				.collect(Collectors.toList());
	}

	public void printSorted(OutputStream out) {
		PrintWriter pw = new PrintWriter(out);

		drivers = drivers.stream()
				.sorted(Comparator.naturalOrder())
				.collect(Collectors.toList());

		for (int i = 0; i < drivers.size(); i++) {
			pw.print(i + 1 + ". ");
			pw.println(drivers.get(i));
		}

		pw.flush();
	}
}


public class F1Test {

	public static void main(String[] args) {
		F1Race f1Race = new F1Race();
		f1Race.readResults(System.in);
		f1Race.printSorted(System.out);
	}

}
