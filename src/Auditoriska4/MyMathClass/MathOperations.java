package Auditoriska4.MyMathClass;

import java.util.*;
import java.util.stream.IntStream;

public class MathOperations {
    public static String statistics(List<? extends Number> numbers) {
        DoubleSummaryStatistics doubleSummaryStatistics =
                numbers.stream() // zemi gi site broevi
                        .mapToDouble(Number::doubleValue) // za sekoje element zemi double vrednost
                        .summaryStatistics(); // generiraj objekt za sekoj
        double standardDeviationTmp = 0;
        for(Number n : numbers) {
            standardDeviationTmp += (n.doubleValue() - doubleSummaryStatistics.getAverage()) *(n.doubleValue() - doubleSummaryStatistics.getAverage());
        }
        double std = Math.sqrt(standardDeviationTmp / numbers.size());

        //cesto se bara
        return String.format("Min: %.2f\nMax: %.2f\nAverage: %.2f\nStandard Deviation: %.2f",
                doubleSummaryStatistics.getMin(),
                doubleSummaryStatistics.getMax(),
                doubleSummaryStatistics.getAverage(),
                std);
    }

    public static void main(String[] args) {
        Random random = new Random();

        List<Integer> list = new ArrayList<Integer>();
        IntStream.range(0, 99999).forEach(i -> list.add(random.nextInt(100)+1));

//        for(int i = 0 ; i < 9999; i++) {
//            list.add(random.nextInt(150)+1);
//        }
        System.out.println(statistics(list));
    }
}
