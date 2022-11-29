package Kolokvium1Vezbi.MobileOperator;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class InvalidIdException extends Exception {
    public InvalidIdException(String message) {
        super(message);
    }
}


abstract class Customer {
    String id;
    double minutes;
    int SMSs;
    double GBs;

    public Customer(String id, double minutes, int SMSs, double GBs) {
        this.id = id;
        this.minutes = minutes;
        this.SMSs = SMSs;
        this.GBs = GBs;
    }

    abstract double totalPrice();

    abstract double commision();
}

class SCustomer extends Customer {

    static double BASE_PRICE_S = 500.0;
    static double FREE_MINUTES_S = 100.0;
    static int FREE_SMS_S = 50;
    static double FREE_GB_INTERNET_S = 5.0;

    static double PRICE_PER_MINUTE = 5.0;
    static double PRICE_PER_SMS = 6.0;
    static double PRICE_PER_GB = 25.0;

    static double COMMISION_RATE = 0.07;

    public SCustomer(String id, double minutes, int SMSs, double GBs) {
        super(id, minutes, SMSs, GBs);
    }

    @Override
    double totalPrice() {
        //Kolku nadminal nad dozvoleni
        double total = BASE_PRICE_S;

        if (minutes > FREE_MINUTES_S) {
            total += (PRICE_PER_MINUTE * (minutes - FREE_MINUTES_S));
        }
        //total += PRICE_PER_MINUTES * Math.max(0, minutes - FREE_MINS_S)
        if (SMSs > FREE_SMS_S) {
            total += (PRICE_PER_SMS * (SMSs - FREE_SMS_S));
        }
        if (GBs > FREE_GB_INTERNET_S) {
            total += (PRICE_PER_GB * (GBs - PRICE_PER_GB));
        }

        return total;
    }

    @Override
    double commision() {
        return totalPrice() * COMMISION_RATE;
    }
}

class MCustomer extends Customer {

    static double BASE_PRICE_M = 750.0;
    static double FREE_MINUTES_M = 150.0;
    static int FREE_SMS_M = 60;
    static double FREE_GB_INTERNET_M = 10.0;

    static double PRICE_PER_MINUTE = 4.0;
    static double PRICE_PER_SMS = 4.0;
    static double PRICE_PER_GB = 20.0;

    static double COMMISION_RATE = 0.04;

    public MCustomer(String id, double minutes, int SMSs, double GBs) {
        super(id, minutes, SMSs, GBs);
    }

    @Override
    double totalPrice() {
        //Kolku nadminal nad dozvoleni
        double total = BASE_PRICE_M;

        if (minutes > FREE_MINUTES_M) {
            total += (PRICE_PER_MINUTE * (minutes - FREE_MINUTES_M));
        }
        //total += PRICE_PER_MINUTES * Math.max(0, minutes - FREE_MINS_S)
        if (SMSs > FREE_SMS_M) {
            total += (PRICE_PER_SMS * (SMSs - FREE_SMS_M));
        }
        if (GBs > FREE_GB_INTERNET_M) {
            total += (PRICE_PER_GB * (GBs - PRICE_PER_GB));
        }

        return total;
    }

    @Override
    double commision() {
        return totalPrice() * COMMISION_RATE;
    }
}

class SalesRep implements Comparable<SalesRep> {
    String id;
    List<Customer> customers;

    public SalesRep(String id, List<Customer> customers) {
        this.id = id;
        this.customers = customers;
    }

    private static boolean isIdValid(String id) {
        if (id.length() != 3) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static SalesRep createSalesRep(String line) throws InvalidIdException {
        // od input line
        String[] parts = line.split("\\s+");// edno ili povekje prazni mesta

        String id = parts[0];
        if (!isIdValid(id)) {
            throw new InvalidIdException(String.format("%s is not a valid sales rep ID", id));
        }

        List<Customer> customers = new ArrayList<>();
        // 0 ID, 1 ID, 2 type, 4 SMS, 5 GB etc
        for (int i = 1; i < parts.length; i += 5) { // string customer id
            String customerID = parts[i];
            String type = parts[i + 1];
            double minutes = Double.parseDouble(parts[i + 2]);
            int sms = Integer.parseInt(parts[i + 3]);
            double gbs = Double.parseDouble(parts[i + 4]);

            if (type.equals("M")) {
                customers.add(new MCustomer(customerID, minutes, sms, gbs));
            } else {
                customers.add(new SCustomer(customerID, minutes, sms, gbs));
            }
        }

        return new SalesRep(id, customers);
    }

    @Override
    public String toString() {
        DoubleSummaryStatistics doubleSummaryStatistics = customers.stream()
                .mapToDouble(Customer::totalPrice)
                .summaryStatistics();

        return String.format("%s Count: %d Min %.2f Average: %.2f Max: %.2f Commission: %.2f",
                id,
                doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getMin(),
                doubleSummaryStatistics.getAverage(),
                doubleSummaryStatistics.getMax(),
                totalCommission());
    }

    private double totalCommission() {
        return customers.stream().mapToDouble(Customer::commision).sum();
    }

    @Override
    public int compareTo(SalesRep o) {
        return Double.compare(this.totalCommission(), o.totalCommission());
    }
}

class MobileOperator {

    List<SalesRep> salesReps;

    void readSalesRepData(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        salesReps = br.lines()
                .map(line -> {
                    try {
                        return SalesRep.createSalesRep(line);
                    } catch (InvalidIdException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        br.close();
    }

    void printSalesReport(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);

        salesReps.stream().sorted(Comparator.reverseOrder())
                .forEach(pw::println);

        pw.flush();
    }
}

public class MobileOperatorTest {


    public static void main(String[] args) {
        MobileOperator mobileOperator = new MobileOperator();
        System.out.println("Reading of the sales reports");
        try {
            mobileOperator.readSalesRepData(System.in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Printing final reports");
        mobileOperator.printSalesReport(System.out);
    }
}
