package Auditoriski8_VtorKolokviumPrva.SieveOfEratosthenes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Eratosthenes {

	private boolean isPrime(int number) {
//		for(int i = 2; i < number; i++)
//			if(number % i == 0) return false;
//		return true;

		return IntStream.range(2, number)
				.noneMatch(i -> number % i == 0);
	}

	void process(List<Integer> numbers) {
		//tesko so streams oti menuvame numbers kako so rabotime so nea
		for (int i = 0; i < numbers.size(); i++) {
			if (isPrime(numbers.get(i))) {
				//ako se najde prost, izbrisi broevi deliteli so toj broj
				for (int j = i + 1; j < numbers.size(); j++) {
					if (numbers.get(j) % numbers.get(i) == 0) {
						numbers.remove(j);
						--j; // bidejki shiftame lista pri brisenje
					}
				}
			}
		}
	}
}

public class EratosthenesTest {
	public static void main(String[] args) {
		//.boxed() zima intstream i pretvora vo stream od integers
		List<Integer> numbers = IntStream.range(2, 1000)
				.boxed().collect(Collectors.toList());

		Eratosthenes eratosthenes = new Eratosthenes();
		eratosthenes.process(numbers);

		System.out.println(numbers);
	}
}
