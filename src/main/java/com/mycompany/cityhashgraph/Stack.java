
package com.mycompany.cityhashgraph;

/**
 *
 * @author Dell
 */
public class Stack {

    public int[] array;
    public int top;

    public Stack(int capacity) {
        array = new int[capacity];
        top = -1;
    }

    public void push(int value) {
        array[++top] = value;
    }

    public int pop() {
        return array[top--];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty. Cannot peek.");
            return -1; // or throw an exception
        }
        return array[top];
    }

        
}
