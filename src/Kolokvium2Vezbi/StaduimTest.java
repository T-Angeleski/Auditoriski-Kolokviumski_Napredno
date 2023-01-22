package Kolokvium2Vezbi;

import java.util.*;

class SeatNotAllowedException extends Exception {

}

class SeatTakenException extends Exception {

}

class Sector {
	String code;
	int seats;
	Map<Integer, Integer> taken;
	Set<Integer> types;

	public Sector(String code, int seats) {
		this.code = code;
		this.seats = seats;
		taken = new HashMap<>();
		types = new HashSet<>();
	}

	public int freeSeats() {
		return seats - taken.size();
	}

	public String getCode() {
		return code;
	}

	public void takeSeat(int seat, int type) throws SeatNotAllowedException {
		if (type == 1) {
			if (types.contains(2))
				throw new SeatNotAllowedException();
		} else if (type == 2)
			if (types.contains(1))
				throw new SeatNotAllowedException();

		types.add(type);
		taken.put(seat, type);
	}

	public boolean isTaken(int seat) {
		return taken.containsKey(seat);
	}

	@Override
	public String toString() {
		double percentage = (seats - freeSeats()) * 100.0 / seats;
		return String.format("%s\t%d/%d\t%.1f%%",
				code,
				freeSeats(),
				seats,
				percentage);
	}
}

class Stadium {
	String name;
	Map<String, Sector> sectors;


	public Stadium(String name) {
		this.name = name;
		sectors = new HashMap<>();
	}

	void createSectors(String[] sectorNames, int[] sizes) {
		for (int i = 0; i < sizes.length; i++) {
			sectors.put(sectorNames[i], new Sector(sectorNames[i], sizes[i]));
		}
	}

	void buyTicket(String sectorName, int seat, int type) throws SeatNotAllowedException, SeatTakenException {
		Sector sector = sectors.get(sectorName);
		if (sector.isTaken(seat))
			throw new SeatTakenException();
		sector.takeSeat(seat, type);
	}

	void showSectors() {
		sectors.values().stream()
				.sorted(Comparator.comparing(Sector::freeSeats).reversed()
						.thenComparing(Sector::getCode))
				.forEach(System.out::println);
	}
}

public class StaduimTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		scanner.nextLine();
		String[] sectorNames = new String[n];
		int[] sectorSizes = new int[n];
		String name = scanner.nextLine();
		for (int i = 0; i < n; ++i) {
			String line = scanner.nextLine();
			String[] parts = line.split(";");
			sectorNames[i] = parts[0];
			sectorSizes[i] = Integer.parseInt(parts[1]);
		}
		Stadium stadium = new Stadium(name);
		stadium.createSectors(sectorNames, sectorSizes);
		n = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < n; ++i) {
			String line = scanner.nextLine();
			String[] parts = line.split(";");
			try {
				stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
						Integer.parseInt(parts[2]));
			} catch (SeatNotAllowedException e) {
				System.out.println("SeatNotAllowedException");
			} catch (SeatTakenException e) {
				System.out.println("SeatTakenException");
			}
		}
		stadium.showSectors();
	}
}

