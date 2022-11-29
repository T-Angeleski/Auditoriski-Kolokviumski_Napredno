package Auditoriska5.FunctionalInterfaces;

import java.util.Random;
import java.util.function.*;

public class FunctionalInterfacesTest {
    public static void main(String[] args) {
        /*Functional interface - interface koj definira tocno edno odnesuvanje ( samo 1 metod)
        Anonymous class - inline definiranje i kreiranje na objekt
        Lambda expression

        Predicate - uslov | filtriraj site studenti so vozrast nad 20
        filter(), findFirst(), allMatch()...*/

        Predicate<Integer> LessThan10 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer < 10;
            }
        };
        Predicate<Integer> getLessThan10 = number -> number < 10;


        // Supplier -> daj rezultat, ne zimaj nisto
        Supplier<Integer> IntegerSupplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt(10000);
            }
        };

        Supplier<Integer> integerSupplier = () -> new Random().nextInt(10000);

        //Consumer - zema nesto, ne vrakja nisto
        // Pechatenje
        Consumer<String> StringConsumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };

        //Tocno znae eden argument prima i toj go pechati
        Consumer<String> stringConsumer = System.out::println; // method reference

        //Function y = 5x   | preslikuvanje
        //Function<input, output>
        Function<Integer, String> AddFiveToNumberAndFormat = new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return String.format("%d\n", integer + 5);
            }
        };

        Function<Integer, String> addFiveToNumberAndFormat = num -> String.format("%d\n", num + 5);


        //BiFunction y = x + z
        BiFunction<Integer, Integer, String> SumNumbersAndFormat = new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2) {
                return String.format("%d + %d = %d\n", integer, integer2, integer + integer2);
            }
        };
        BiFunction<Integer, Integer, String> sumNumbersAndFormat = (integer, integer2) -> String.format("%d + %d = %d\n", integer, integer2, integer + integer2);

    }
}
