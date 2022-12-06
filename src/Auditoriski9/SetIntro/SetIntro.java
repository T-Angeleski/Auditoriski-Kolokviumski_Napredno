package Auditoriski9.SetIntro;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

//class Student {
//	String id;
//	int grades;
//
//	public Student(String id, int grades) {
//		this.id = id;
//		this.grades = grades;
//	}
//
//
//}


public class SetIntro {
	public static void main(String[] args) {
		//Nema duplikati vo sets

		Set<String> set;

		//nekakva vrednost so hashCode
		//dokolku imaat ista sodrzina

		//O(1) add, remove, search O(n) iterating
//		set = new HashSet<String>();
//		String s1 = "Stefan";
//		String s2 = "Stefan";
//
//		set.add(s1);
//		set.add(s2); // ist hashcode, kje zameni s1
//		set.add("NP");
//		set.add("Napredno programiranje");
//
//		//Redosled zavisi od hashCode
//		//Ako ni treba da cuvame redosled -> LinkedHashSet (vtor vid)
//		System.out.println(set);


		//TreeSet -> sortirani spored nekoj comparator
		//treba da imaat comparable objektite
		set = new TreeSet<>((l, r) -> l.compareToIgnoreCase(r));
		String s1 = "Stefan";
		String s2 = "stefan"; //pak nema da dodade poradi ignorecase
		set.add(s1);
		set.add(s2);
		set.add("NP");
		set.add("Napredno programiranje");

		//Leksikografski podredeni (a -> b -> c etc)
		System.out.println(set);
	}
}
