package Auditoriska4.PriorityQueue;

public class PriorityQueueElement<E> implements Comparable<PriorityQueueElement<E>>{
    private E element;
    private int priority;

    public PriorityQueueElement(E element, int priority) {
        this.element = element;
        this.priority = priority;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format("%s : %d", element.toString(), priority);
    }

    @Override
    public int compareTo(PriorityQueueElement<E> o) {
        return Integer.compare(this.priority, o.priority);
    }
}
