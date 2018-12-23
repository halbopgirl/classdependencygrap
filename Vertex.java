// Vertex.java
//Name: Haleigh Jayde Doetschman
//Date: 12/16/18
//Class: CMSC 350 Fall 2018
//Purpose: Defines the Vertex object and its methods
package classdependencygraph;

public class Vertex<E> {
    //declare variables
    private E name;
    private boolean isDiscovered;
    private boolean isFinished;

    //Vertex constructor 
    public Vertex(E name) {
        this.name = name;
        isDiscovered = false;
        isFinished = false;
    }
    
    //getters and setters
    
    public E getName() {
        return name;
    }

    public boolean hasBeenDiscovered() {
        return isDiscovered;
    }

    public void setDiscovered() {
        isDiscovered = true;
    }

    public boolean hasFinished() {
        return isFinished;
    }

    public void setFinished() {
        isFinished = true;
    }

    public String reset() {
        isDiscovered = false;
        isFinished = false;
        return "vertex reset";
    }

}
