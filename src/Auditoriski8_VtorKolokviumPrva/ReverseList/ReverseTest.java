package Auditoriski8_VtorKolokviumPrva.ReverseList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReverseTest {
	public static void main(String[] args) {
		List<String> list = new ArrayList<>(List.of("A", "B", "C", "D"));

		Collections.reverse(list);
		System.out.println(list);
	}
}
