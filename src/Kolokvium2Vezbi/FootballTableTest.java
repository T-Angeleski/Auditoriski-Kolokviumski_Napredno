package Kolokvium2Vezbi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class FootballTeam {
	String name;
	int wins;
	int ties;
	int losses;
	int totalPoints;
	int goalsGiven;
	int goalsTaken;

	public FootballTeam(String name) {
		this.name = name;
		wins = ties = losses = 0;
		totalPoints = 0;
		goalsGiven = goalsTaken = 0;
	}

	void updateInformation(int goalsGiven, int goalsReceived) {
		if (goalsGiven > goalsReceived)
			wins++;
		else if (goalsGiven == goalsReceived)
			ties++;
		else losses++;
		this.goalsGiven += goalsGiven;
		this.goalsTaken += goalsReceived;
	}

	int getPoints() {
		return wins * 3 + ties;
	}

	int getTotalGames() {
		return wins + ties + losses;
	}

	int goalDifference() {
		return goalsGiven - goalsTaken;
	}

	String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("%-15s%5d%5d%5d%5d%5d",
				name,
				getTotalGames(),
				wins,
				ties,
				losses,
				getPoints());
	}
}

class FootballTable {
	Map<String, FootballTeam> teamsByName;

	public FootballTable() {
		teamsByName = new HashMap<>();
	}

	public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
		FootballTeam home = teamsByName.computeIfAbsent(homeTeam, key -> new FootballTeam(homeTeam));
		FootballTeam away = teamsByName.computeIfAbsent(awayTeam, key -> new FootballTeam(awayTeam));

		home.updateInformation(homeGoals, awayGoals);
		away.updateInformation(awayGoals, homeGoals);
	}

	public void printTable() {
		List<FootballTeam> result = teamsByName.values().stream()
				.sorted(Comparator.comparing(FootballTeam::getPoints)
						.thenComparing(FootballTeam::goalDifference).reversed()
						.thenComparing(FootballTeam::getName))
				.collect(Collectors.toList());

		IntStream.range(0, result.size())
				.forEach(i -> System.out.printf("%2d. %s\n",
						i + 1,
						result.get(i)));
	}
}


/**
 * Partial exam II 2016/2017
 */
public class FootballTableTest {
	public static void main(String[] args) throws IOException {
		FootballTable table = new FootballTable();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		reader.lines()
				.map(line -> line.split(";"))
				.forEach(parts -> table.addGame(parts[0], parts[1],
						Integer.parseInt(parts[2]),
						Integer.parseInt(parts[3])));
		reader.close();
		System.out.println("=== TABLE ===");
		System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
		table.printTable();
	}
}

// Your code here


