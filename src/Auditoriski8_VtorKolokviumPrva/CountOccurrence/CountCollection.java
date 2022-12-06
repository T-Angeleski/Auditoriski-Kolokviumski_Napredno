package Auditoriski8_VtorKolokviumPrva.CountOccurrence;

import java.util.Collection;

public class CountCollection {

	public static int count(Collection<Collection<String>> collections, String str) {
		int counter = 0;
		for (Collection<String> collection : collections) {
			for (String element : collection) {
				if (element.equals(str))
					counter++;
			}
		}
		return counter;
	}

	public static int count2(Collection<Collection<String>> collections, String str) {
		//flatmap za kolekcija od kolekcii
		return (int) collections.stream() //stream od collections
				.flatMap(collection -> collection.stream())//za sekoj collection, stream
					.filter(element -> element.equals(str))
					.count();
	}

	public static void main(String[] args) {

	}
}
