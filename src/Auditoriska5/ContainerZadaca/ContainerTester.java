package Auditoriska5.ContainerZadaca;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

interface Weightable extends Comparable<Weightable> {
	double getWeight();

	@Override
	default int compareTo(Weightable o) {
		return Double.compare(this.getWeight(), o.getWeight());
	}
}

class WeightableDouble implements Weightable {
	double weight;

	public WeightableDouble(double weight) {
		this.weight = weight;
	}

	@Override
	public double getWeight() {
		return weight;
	}

}

class WeightableString implements Weightable {
	String word;

	public WeightableString(String word) {
		this.word = word;
	}

	@Override
	public double getWeight() {
		return word.length();
	}
}

class Container<T extends Weightable> {
	private List<T> elements;

	public Container() {
		this.elements = new ArrayList<>();
	}

	public void addElement(T element) {
		elements.add(element);
	}

	public List<T> lighterThan(T element) {
		return elements.stream()
				.filter(i -> i.compareTo(element) < 0)
				.toList();
	}

	public List<T> between(T a, T b) {
		return elements.stream()
				.filter(i -> i.compareTo(a) > 0 && i.compareTo(b) < 0)
				.toList();
	}

	public double containerSum() {
		return elements.stream()
				.mapToDouble(Weightable::getWeight) // T ne znaeme da sobirame, brojki znaeme
				.sum();
	}

	public int compare(Container<? extends Weightable> container2) {
		return Double.compare(this.containerSum(), container2.containerSum());
	}

}


public class ContainerTester {
	public static void main(String[] args) {
		System.out.println("Code daden od kolokvium");
	}
}
