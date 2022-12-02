package Kolokvium1Vezbi.NumberProcessor;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

interface NumberProcessor<IN extends Number, R> {
	R compute(ArrayList<IN> numbers);
}


class Numbers<N extends Number> {

	ArrayList<N> numbers;

	public Numbers(ArrayList<N> numbers) {
		this.numbers = numbers;
	}

	// ?, any object type can be returned
	// other method
	//<R>	void process(NumberProcessor<N, R> processor) {
	void process(NumberProcessor<N, ?> processor) {
		System.out.println(processor.compute(numbers));
	}
}

public class NumberProcessorTest {

	public static void main(String[] args) {

		//Lambda or anonymous
		NumberProcessor<Integer, Long> firstProcessor = numbers -> numbers.stream().filter(n -> n < 0).count();

		NumberProcessor<Integer, String> secondProcessor = numbers -> {
			DoubleSummaryStatistics sumStats = numbers.stream()
					.mapToDouble(i -> i).summaryStatistics();
			return String.format("Count: %d Min: %.2f Avg: %.2f Max: %.2f",
					sumStats.getCount(),
					sumStats.getMin(),
					sumStats.getAverage(),
					sumStats.getMax());
		};

		//Ovie se double
		NumberProcessor<Double, List<Double>> thirdProcessor = numbers -> {
			return numbers.stream().sorted().collect(Collectors.toList());
		};

		//Median, paren broj -> prosek od dvata sredni
		NumberProcessor<Double, Double> fourthProcessor = numbers -> {
			//tret procesor vekje sortira
			List<Double> sorted = thirdProcessor.compute(numbers);
			int size = sorted.size();
			if (size % 2 == 0) {
				// n/2 - 1 , n/2
				return (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0;
			} else {
				return sorted.get(size / 2);
			}
		};


	}
}
