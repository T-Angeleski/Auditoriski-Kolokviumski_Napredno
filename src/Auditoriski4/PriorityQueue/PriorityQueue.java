package Auditoriski4.PriorityQueue;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<T> {
    //od tip List za po-generichki da e (mozeme od ova ->array, ->linked)
    private List<PriorityQueueElement<T>> elements;

    public PriorityQueue() {
        this.elements = new ArrayList<>(); // ovde ja definirame kako array list
    }

    public void add(T item, int priority) {
        PriorityQueueElement<T> element = new PriorityQueueElement<>(item, priority);
        int i = 0;
        for(i = 0 ; i < elements.size(); i++)
            if(element.compareTo(elements.get(i)) <= 0) break;
        elements.add(i, element);
    }

    public T remove() {
        if(elements.size() == 0 ) return null;
        return elements.remove(elements.size() - 1).getElement(); // T bez getElement
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        elements.forEach(i -> stringBuilder.append(i.toString()).append(" "));
        return stringBuilder.toString();
    }
}
