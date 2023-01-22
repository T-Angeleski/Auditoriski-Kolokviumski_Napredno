package Kolokvium2Vezbi;

import java.util.*;
import java.util.stream.Collectors;

class Movie {
	String title;
	List<Integer> ratings;

	public Movie(String title, List<Integer> ratings) {
		this.title = title;
		this.ratings = ratings;
	}

	public String getTitle() {
		return title;
	}

	public double averageRating() {
		return ratings.stream()
				.mapToInt(i -> i)
				.average().orElse(0);
	}

	public double ratingCoef() {
		return averageRating() * ratings.size();
	}

	@Override
	public String toString() {
		return String.format("%s (%.2f) of %d ratings",
				title,
				averageRating(),
				ratings.size());
	}
}

class MoviesList {
	List<Movie> movies;

	public MoviesList() {
		movies = new ArrayList<>();
	}

	public void addMovie(String title, int[] ratings) {
		List<Integer> ratingsList = new ArrayList<>();
		for (int rating : ratings) {
			ratingsList.add(rating);
		}
		movies.add(new Movie(title, ratingsList));
	}

	public List<Movie> top10ByAvgRating() {
		return movies.stream()
				.sorted(Comparator.comparing(Movie::averageRating).reversed()
						.thenComparing(Movie::getTitle))
				.limit(10)
				.collect(Collectors.toList());
	}

	private int maxRatings() {
		return movies.stream()
				.mapToInt(i -> i.ratings.size())
				.max().orElse(1);
	}


	public List<Movie> top10ByRatingCoef() {
		List<Movie> filtered = movies.stream()
				.sorted(new CoefComparator(maxRatings()))
				.limit(10)
				.collect(Collectors.toList());

//		List<Movie> result = new ArrayList<>();
//		int size = filtered.size();
//		for (int i = size- 1; i >= size - 10; i--) {
//			result.add(filtered.get(i));
//		}
		return filtered;
	}
}

class CoefComparator implements Comparator<Movie> {
	int max;

	public CoefComparator(int max) {
		this.max = max;
	}

	@Override
	public int compare(Movie o1, Movie o2) {
		int ar = Double.compare(o1.averageRating() * o1.ratings.size() / max, o2.averageRating() * o2.ratings.size() / max);
		if (ar == 0) {
			return o1.title.compareTo(o2.title);
		}
		return -ar;
	}
}

public class MoviesTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		MoviesList moviesList = new MoviesList();
		int n = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < n; ++i) {
			String title = scanner.nextLine();
			int x = scanner.nextInt();
			int[] ratings = new int[x];
			for (int j = 0; j < x; ++j) {
				ratings[j] = scanner.nextInt();
			}
			scanner.nextLine();
			moviesList.addMovie(title, ratings);
		}
		scanner.close();
		List<Movie> movies = moviesList.top10ByAvgRating();
		System.out.println("=== TOP 10 BY AVERAGE RATING ===");
		for (Movie movie : movies) {
			System.out.println(movie);
		}
		movies = moviesList.top10ByRatingCoef();
		System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
		for (Movie movie : movies) {
			System.out.println(movie);
		}
	}
}

