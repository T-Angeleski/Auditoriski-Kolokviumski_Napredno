package Auditoriski4.PriorityQueue;

public class PriorityQueueTester {

    public static void main(String[] args) {

        ;
        PriorityQueue<String> stringPriorityQueue = new PriorityQueue<String>();
        stringPriorityQueue.add("string1", 89);
        stringPriorityQueue.add("string2", 50);
        stringPriorityQueue.add("string3", 1);
        stringPriorityQueue.add("string4", 84);
        System.out.println(stringPriorityQueue);

        PriorityQueue<Integer> integerPriorityQueue = new PriorityQueue<Integer>();
        integerPriorityQueue.add(2,11);
        integerPriorityQueue.add(5,50);
        integerPriorityQueue.add(1,99);
        integerPriorityQueue.add(8,48);
        System.out.println(integerPriorityQueue);
    }

}
