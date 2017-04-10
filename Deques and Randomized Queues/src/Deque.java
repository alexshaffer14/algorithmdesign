/**
 * Created by Alex Shaffer
 * February 31, 2017
 * Assignment 2: Randomized Deques and Queues
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
 
public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;
 
    public Deque() {
        // construct an empty deque
        first = null;
        last = first;
        size = 0;
    }
 
    public boolean isEmpty() {
        // is the deque empty?
        return size == 0;
    }
 
    public int size() {
        // return the number of items on the deque
        return size;
    }
 
    public void addFirst(Item item) {
        // add the item to the front
        if (item == null) {
            throw new NullPointerException();
        }
        Node node = new Node(item);
        if (isEmpty()) {
            last = node;
            first = node;
        } else {
            node.next = first;
            first.previous = node;
            first = node;
        }
        size++;
    }
 
    public void addLast(Item item) {
        // add the item to the end
        if (item == null) {
            throw new NullPointerException();
        }
        Node node = new Node(item);
        if (isEmpty()) {
            last = node;
            first = node;
        } else {
            last.next = node;
            node.previous = last;
            last = node;
        }
        size++;
    }
 
    public Item removeFirst() {
        // remove and return the item from the front
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.previous = null;
        }
        size--;
        return item;
    }
 
    public Item removeLast() {
        // remove and return the item from the end
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            last = last.previous;
            last.next = null;
        }
        size--;
        return item;
    }
 
    public Iterator<Item> iterator() { 
        Iterator<Item> it = new Iterator<Item>() {
            private Node current = first;
            public boolean hasNext() {
                return current != null;
            }
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Item item = (Item) current.item;
                current = current.next;
                return item;
            }
            public void remove() { 
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
 
    private class Node {
        private Item item;
        private Node next, previous;
 
        public Node(Item item) {
            this.item = item;
        }
    }
}