package Kolokvium1Vezbi.F1Trka;


import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class Lap implements Comparable<Lap> {
	int mins;
	int seconds;
	int nanoseconds;

	public Lap(int mins, int seconds, int nanoseconds) {
		this.mins = mins;
		this.seconds = seconds;
		this.nanoseconds = nanoseconds;
	}

	@Override
	public String toString() {
		return String.format("%d:%02d:%03d", mins, seconds, nanoseconds);
	}

	public long convertToNanoSeconds() {
		return (long) nanoseconds + (long) seconds * 1000000000 + (long) mins * 60 * 1000000000;
	}

	@Override
	public int compareTo(Lap o) {
		return Long.compare(o.convertToNanoSeconds(), this.convertToNanoSeconds());
	}
}

class Driver implements Comparable<Driver> {
	private String driverName;
	List<Lap> laps;

	public Driver() {
		this.laps = new ArrayList<>();
	}

	public Driver(String driverName, List<Lap> laps) {
		this.driverName = driverName;
		this.laps = laps;
	}

	public String getDriverName() {
		return driverName;
	}

	private static Lap createLap(String text) {
		String[] parts = text.split(":");
		int mins = Integer.parseInt(parts[0]);
		int seconds = Integer.parseInt(parts[1]);
		int ns = Integer.parseInt(parts[2]);
		return new Lap(mins, seconds, ns);
	}

	public static Driver addRace(String line) {
		//Vetel 1:55:523 1:54:987 1:56:134
		String[] parts = line.split("\\s+");
		String name = parts[0];
		List<Lap> laps = new ArrayList<>();

		for (int i = 1; i < 4; i++) {
			laps.add(createLap(parts[i]));
		}

		return new Driver(name, laps);
	}

	private String printLaps() {
		StringBuilder sb = new StringBuilder();
		for (Lap lap : laps)
			sb.append(lap.toString()).append(" ");
		return sb.toString();
	}

	@Override
	public String toString() {
		return String.format("%s %s",
				driverName,
				printLaps());
	}

	public Lap findSmallestLap(List<Lap> laps) {
		return laps.stream().max(Comparator.naturalOrder()).orElse(null);
	}

	@Override
	public int compareTo(Driver o) {
		return this.findSmallestLap(laps).compareTo(o.findSmallestLap(o.laps));
	}
}

class F1Race {
	List<Driver> drivers;

	public F1Race() {
		this.drivers = new ArrayList<>();
	}

	public F1Race(List<Driver> drivers) {
		this.drivers = drivers;
	}

	public void readResults(InputStream in) {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		drivers = br.lines()
				.map(Driver::addRace)
				.collect(Collectors.toList());
	}

	public void printSorted(PrintStream out) {
		PrintWriter pw = new PrintWriter(out);

		drivers = drivers.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

		for (int i = 0; i < drivers.size(); i++) {
			pw.println(String.format("%d. %-10s%10s",
					i + 1,
					drivers.get(i).getDriverName(),
					drivers.get(i).findSmallestLap(drivers.get(i).laps)));
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

