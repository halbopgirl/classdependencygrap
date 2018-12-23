//StackException.java
//Name: Haleigh Jayde Doetschman
//Date: 12/16/18
//Class: CMSC 350 Fall 2018
//Purpose: Throws an exception when something goes wrong while using the stack

package classdependencygraph;

import javax.swing.JOptionPane;

public class StackException extends Exception {
   
    public StackException() {   
        JOptionPane.showMessageDialog(null, "Stack Error");
    }
    
    //custom StackException
    public StackException (String message) {
        System.out.println(message);
        JOptionPane.showMessageDialog(null, message);
    }
}
