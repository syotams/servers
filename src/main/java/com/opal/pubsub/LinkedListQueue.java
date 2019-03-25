package com.opal.pubsub;

import java.util.Collection;
import java.util.Iterator;

/**
 * This class is partially implementation for Linked List, just for study purposes!
 * In real world we would use LinkedList / other Queue interface implementation
 *
 * @param <T>
 */
public class LinkedListQueue<T> implements Iterable<T> {

    private class Node {
        private T item;
        private Node next;
        private Node prev;

        Node(T item) {
            this.item = item;
        }
    }

    private Node head;
    private Node last;
    private volatile int size; // if we didn't synchronize our method, the appropriate is to use AtomicInteger


    public synchronized void enqueue(T item) {
        if(null == head) {
            last = head = new Node(item);
        }
        else {
            Node node = new Node(item);
            node.prev = last;
            last.next = node;
            last = last.next;
        }

        size++;

        notifyAll();
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     */
    public synchronized T dequeue() {
        if(null==head) return null;

        Node node = head;
        head = head.next;
        node.prev = null;

        if(null!=head) {
            head.prev = null;
        }

        size--;

        return node.item;
    }

    public synchronized int size() {
        return size;
    }

    public synchronized boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(T item) {
        return null != find(item);
    }

    private synchronized Node find(T item) {
        for(Node node = head; head.next!=null; node = node.next) {
            if(item.equals(node.item)) {
                return node;
            }
        }

        return null;
    }

    public boolean remove(T item) {
        Node node = find(item);

        if(null == node) return false;

        synchronized(this) {
            if (head == node) {
                return null != dequeue();
            }

            node.prev.next = node.next;
            node.next.prev = node.prev;

            size--;
        }

        return true;
    }

    public synchronized void reversePrint() {
        for(Node node = last; node!=null; node = node.prev) {
            System.out.println(node.item);
        }
    }

    // @TODO
    public boolean addAll(Collection collection) {
        return false;
    }

    // @TODO
    public void clear() {}

    // Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
    public synchronized T peek() {
        return null != head ? head.item : null;
    }

    public Iterator<T> iterator() {
        return new ListIterator(this);
    }

    class ListIterator implements Iterator<T> {
        Node current;

        // initialize pointer to head of the list for iteration
        public ListIterator(LinkedListQueue<T> list)
        {
            current = list.head;
        }

        // returns false if next element does not exist
        public boolean hasNext()
        {
            return current != null;
        }

        // return current data and update pointer
        public T next()
        {
            T data = current.item;
            current = current.next;
            return data;
        }

        // implement if needed
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
