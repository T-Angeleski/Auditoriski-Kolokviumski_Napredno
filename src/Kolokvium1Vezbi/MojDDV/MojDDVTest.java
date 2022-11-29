package Kolokvium1Vezbi.MojDDV;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

enum TaxType {
	A, B, V
}

class AmountNotAllowedException extends Exception {
	public AmountNotAllowedException(int amount) {
		super(String.format("Receipt with amount %d is not allowed to be scanned", amount));
	}
}

class Item {
	private int price;
	private TaxType tax;

	public Item(int price, TaxType tax) {
		this.price = price;
		this.tax = tax;
	}

	public Item(int price) {
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public TaxType getTax() {
		return tax;
	}

	public void setTax(TaxType tax) {
		this.tax = tax;
	}

	public double getCalculatedTax() {
		if (tax.equals(TaxType.A)) return 0.18 * price * 0.15;
		else if (tax.equals(TaxType.B)) return 0.05 * price * 0.15;
		else return 0;
	}
}

class Receipt implements Comparable<Receipt> {
	private long id;
	private List<Item> items;

	public Receipt(long id, List<Item> items) {
		this.id = id;
		this.items = items;
	}

	public static Receipt create(String line) throws AmountNotAllowedException {
		String[] parts = line.split("\\s+");
		long id = Long.parseLong(parts[0]);
		List<Item> items = new ArrayList<>();

		Arrays.stream(parts)
				.skip(1)
				.forEach(i -> {
					if (Character.isDigit(i.charAt(0))) {
						//kaj brojot
						items.add(new Item(Integer.parseInt(i)));
					} else {
						//zemi posleden dodaden item, stavi mu tax toj i toj (od string vo type)
						items.get(items.size() - 1).setTax(TaxType.valueOf(i));
					}
				});

		if (totalAmount(items) > 30000)
			throw new AmountNotAllowedException(totalAmount(items));
		return new Receipt(id, items);
	}

	//static za da ne piseme vo drugiot metod
	public static int totalAmount(List<Item> items) {
		return items.stream()
				.mapToInt(Item::getPrice)
				.sum();
	}

	public int totalAmount() {
		return items.stream()
				.mapToInt(Item::getPrice)
				.sum();
	}

	public double taxReturns() {
		return items.stream()
				.mapToDouble(Item::getCalculatedTax)
				.sum();
	}

	public Receipt(long id) {
		this.id = id;
		this.items = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	@Override
	public int compareTo(Receipt o) {
		return Comparator.comparing(Receipt::taxReturns)
				.thenComparing(Receipt::totalAmount)
				.compare(this, o);
	}

	@Override
	public String toString() {
		return String.format("%d %d %.2f", id, totalAmount(), taxReturns());
	}
}

class MojDDV {

	private List<Receipt> receipts;

	public MojDDV() {
		this.receipts = new ArrayList<>();
	}

	public void readRecords(InputStream in) {
		receipts = new BufferedReader(new InputStreamReader(in))
				.lines()
				.map(line -> { // od string linija vo objekt receipt
					try {
						return Receipt.create(line);
					} catch (AmountNotAllowedException e) {
						System.out.println(e.getMessage());
						return null;
					}
				})
				.collect(Collectors.toList());

		receipts = receipts.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	public void printSorted(OutputStream out) {
		PrintWriter pw = new PrintWriter(out);

		receipts.stream()
				.sorted()
				.forEach(i -> pw.println(i.toString()));

		pw.flush();
	}

	public void printTaxReturns(OutputStream out) {
		PrintWriter pw = new PrintWriter(out);

//		DoubleSummaryStatistics statistics = receipts.stream()
//				.mapToDouble(Receipt::taxReturns)
//				.summaryStatistics();
//
//		pw.println(String.format("Min: %.2f Max %.2f Sum %.2f Count %d Average %.2f",
//				statistics.getMin(),
//				statistics.getMax(),
//				statistics.getSum(),
//				statistics.getCount(),
//				statistics.getAverage()));

		for (Receipt receipt : receipts) {
			pw.println(receipt);
		}
		pw.flush();
	}
}

public class MojDDVTest {
	public static void main(String[] args) {
		MojDDV mojDDV = new MojDDV();

		System.out.println("===READING RECORDS FROM INPUT STREAM===");
		mojDDV.readRecords(System.in);

		System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
		mojDDV.printTaxReturns(System.out);
	}
}
