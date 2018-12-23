//Stack.java
//Name: Haleigh Jayde Doetschman
//Date: 12/16/18
//Class: CMSC 350 Fall 2018
//Purpose: Generic interface which includes methods to be used in ArrayStack

package classdependencygraph;

public interface Stack <E> {
public boolean isFull();
    public boolean isEmpty();
    public E peek() throws StackException;
    public void push (E item) throws StackException;
    public E pop() throws StackException;    
}
