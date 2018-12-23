//cycleException.java
//Name: Haleigh Jayde Doetschman
//Date: 12/16/18
//Class: CMSC 350 Fall 2018
//Purpose: Special exception to be used when a cycle occurs in a graph to prevent overflow

package classdependencygraph;

import javax.swing.JOptionPane;

public class cycleException extends Exception{
    //for cases when a cycle occurs
    public cycleException () {
        JOptionPane.showMessageDialog(null, "This Graph Contains a Cycle!");
    }
}
