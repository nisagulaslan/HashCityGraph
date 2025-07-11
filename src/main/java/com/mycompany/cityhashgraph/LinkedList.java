
package com.mycompany.cityhashgraph;

/**
 *
 * @author Dell
 */
public class LinkedList<T> {
    Node<T> first;
    Node<T> last;

    public LinkedList() {
        first = null;
        last = null;
    }

    public void insertFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = first;
        first = newNode;
    }

    public void insertLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (last == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
    }

    public void insertAfter(T data, Node<T> prev) {
        Node<T> newNode = new Node<>(data);
        newNode.next = prev.next;
        prev.next = newNode;
    }

    public Node<T> search(T data) {
        Node<T> tmp = first;
        while (tmp != null) {
            if (tmp.data.equals(data)) {
                return tmp;
            }
            tmp = tmp.next;
        }
        return null;
    }

    public void removeFirst() {
        if (first != null) {
            first = first.next;
        }
    }

    public void removeLast() {
        if (first == null) {
            return;
        }

        if (first.next == null) {
            first = null;
            last = null;
            return;
        }

        Node<T> tmp = first;
        while (tmp.next != last) {
            tmp = tmp.next;
        }
        tmp.next = null;
        last = tmp;
    }

    public void removeAfter(Node<T> prev) {
        if (prev != null && prev.next != null) {
            prev.next = prev.next.next;
        }
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
