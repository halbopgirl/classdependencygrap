//invalidClassException
//Name: Haleigh Jayde Doetschman
//Date: 12/16/18
//Class: CMSC 350 Fall 2018
//Purpose: Special exception class to throw an exception when a class is not present


package classdependencygraph;

import javax.swing.JOptionPane;

public class invalidClassException extends Exception{
    
    //for when an invalid class name is specified
    public invalidClassException () {
        JOptionPane.showMessageDialog(null, "Class Not Found");
    }
    
}
