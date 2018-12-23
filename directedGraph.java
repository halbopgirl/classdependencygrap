//directedGraph.java
//Name: Haleigh Jayde Doetschman
//Date: 12/16/18
//Class: CMSC 350 Fall 2018
//Purpose: Defines the directedGraph and includes its methods
package classdependencygraph;

import java.util.*;

public class directedGraph<E> {

    //variable declarations
    private Vertex vertex;
    private static int key = 0;
    private static int key2 = 0;
    private ArrayStack<Vertex> myStack = new ArrayStack();
    private LinkedList<Integer> indexList;
    public ArrayList<LinkedList<Integer>> listofVertices;
    public HashMap<E, Integer> vertexNameIndex;
    private HashMap<Integer, E> nameVertexIndex;
    private HashMap<Vertex, Integer> vertexKeyIndex;
    private HashMap<Integer, Vertex> keyVertexIndex;
    public String outString = new String("");

    //constructor to initialize directed graph
    public directedGraph() {
        listofVertices = new ArrayList<>();
        vertexNameIndex = new HashMap<>();
        nameVertexIndex = new HashMap<>();
        vertexKeyIndex = new HashMap<>();
        keyVertexIndex = new HashMap<>();
        outString = this.outString;
    }//end constructor

    //method to reset the keys to zero
    public void resetKeys() {
        key = 0;
        key2 = 0;
    }//end resetKeys

    //method to reset hasBeenDiscovered and hasFinished for vertexes in Hashmap
    public void resetKeyVertexIndex() {
        for (Vertex v : keyVertexIndex.values()) {
            v.reset();
        }
    }//end resetKeyVertexIndex

    //method to add a vertex to the graph
    public void addVertex(directedGraph graph, E vertex) {
        if (graph.vertexNameIndex.containsKey(vertex)) {
            //do nothing
        } else {
            key++;
            graph.vertexNameIndex.put(vertex, key);
            graph.vertexKeyIndex.put(new Vertex(vertex), key);
        }
        if (graph.nameVertexIndex.containsKey(key)) {
            //do nothing
        } else {
            key2++;
            graph.nameVertexIndex.put(key, vertex);
            graph.keyVertexIndex.put(key, new Vertex(vertex));
        }
    }//end addVertex

    //method to add an edge to the graph
    public HashMap addEdge(directedGraph graph, E vertex1, E vertex2) {
        if (!graph.vertexNameIndex.containsKey(vertex1)) {
            graph.addVertex(graph, vertex1);
            //add vertex to hashmap
            key++;
            graph.vertexNameIndex.put(vertex1, key);
            graph.vertexKeyIndex.put(new Vertex(vertex1), key);
        }
        if (!graph.vertexNameIndex.containsKey(vertex2)) {
            //add vertex to hashmap
            key++;
            graph.vertexNameIndex.put(vertex2, key);
            graph.vertexKeyIndex.put(new Vertex(vertex2), key);
        }
        if (!graph.nameVertexIndex.containsValue(vertex1)) {
            graph.addVertex(graph, key);
            //add vertex to hashmap
            key2++;
            graph.nameVertexIndex.put(key, vertex1);
            graph.keyVertexIndex.put(key, new Vertex(vertex1));
        }
        if (!graph.nameVertexIndex.containsValue(vertex2)) {
            //add vertex to hashmap
            key2++;
            graph.nameVertexIndex.put(key, vertex2);
            graph.keyVertexIndex.put(key, new Vertex(vertex2));
        }
        return vertexNameIndex;
    }//end addEdge

    public LinkedList<Integer> createList(String[] line, HashMap vertexNameIndex) {
        LinkedList<Integer> thisList = new LinkedList();
        for (int i = 0; i < line.length; i++) {
            if (vertexNameIndex.containsKey(line[i])) {
                thisList.add(((Integer) vertexNameIndex.get(line[i])));
            }//end if
        }//end for
        return thisList;
    }//end createList

    //method to generate a topological order
    public String depthFirstSearch(Vertex vertex, directedGraph graph) throws cycleException, StackException {
        int vertexKey = vertexNameIndex.get(vertex.getName());
        if (vertex.hasBeenDiscovered()) {
            throw new cycleException();
        }
        if (vertex.hasFinished()) {
            return outString;
        }
        vertex.setDiscovered();
        //for all LinkedLists in ArrayList
        try {
            for (int i = 0; i < graph.listofVertices.size(); i++) {
                //if the first class's key matches the vertex's key
                if (listofVertices.get(i).getFirst() == vertexKey) {
                    //get all keys in the row
                    for (int j = 1; j < listofVertices.get(i).size(); j++) {
                        int edgeKey = listofVertices.get(i).get(j);
                        Vertex vertexInQuestion = keyVertexIndex.get(edgeKey);
                        depthFirstSearch(vertexInQuestion, graph);
                    }//end nested for
                }//end if
            }//end for loop
            vertex.setFinished();
            myStack.push(vertex);
            while (!myStack.isEmpty()) {
                outString = myStack.pop().getName() + " " + outString;
            }//end while
        } catch (StackException ex) {
            throw new cycleException();
        }//end try catch
        return outString;
    }//end depthFirstSearch

}//end class
