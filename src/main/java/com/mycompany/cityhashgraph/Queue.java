
package com.mycompany.cityhashgraph;

/**
 *
 * @author Dell
 */
public class Queue<T> {
    private int size; // size of the queue
    private Node<T> first;
    private Node<T> last;

    public boolean isEmpty() {
        return first == null;
    }

    // insert at the end of the queue
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    // remove from the beginning
    public T dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Queue Underflow");
        }
        Node<T> tmp = first;
        first = first.next;
        // tmp.next = null;
        size--;
        return tmp.data; // return the saved item
    }

    public int size() {
        return size;
    }

    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
}
