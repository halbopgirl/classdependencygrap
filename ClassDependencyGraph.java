//ClassDependencyGraph.java
//Name: Haleigh Jayde Doetschman
//Date: 12/16/18
//Class: CMSC 350 Fall 2018
//Purpose: Implements the GUI and its functionality and runs the program in the main method

package classdependencygraph;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ClassDependencyGraph {

//user panel elements and variables
    private final JLabel importFileLabel = new JLabel("Input file name: ");
    private final JLabel classToRecompileLabel = new JLabel("Class to recompile: ");
    private final JLabel recompilationOrder = new JLabel("Recompilation Order");
    private JTextField inputFile = new JTextField();
    private JTextField chooseClass = new JTextField();
    private JTextField recompilation = new JTextField();
    private final JButton buildGraph = new JButton("Build Directed Graph");
    private final JButton orderButton = new JButton("Topological Order");
    private HashMap vertexNameIndex;
    private LinkedList<Integer> vertexes = new LinkedList();
    private static directedGraph graph = new directedGraph();
    private ArrayStack stack = new ArrayStack();

    //create interface
    public ClassDependencyGraph() {

        //big panel
        JPanel guiPanel = new JPanel();
        GroupLayout layout = (new GroupLayout(guiPanel));
        guiPanel.setLayout(layout);
        guiPanel.add(importFileLabel);
        guiPanel.add(classToRecompileLabel);
        guiPanel.add(recompilationOrder);
        guiPanel.add(inputFile);
        guiPanel.add(chooseClass);
        guiPanel.add(recompilation);
        guiPanel.add(buildGraph);
        guiPanel.add(orderButton);

        //layout for GUI
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGap(20, 20, 20)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(importFileLabel, 100, 400, 400)
                        .addComponent(inputFile, 100, 400, 400)
                        .addGap(50)
                        .addComponent(buildGraph, 100, 400, 400))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(classToRecompileLabel, 100, 400, 400)
                        .addComponent(chooseClass, 200, 400, 400)
                        .addGap(20)
                        .addComponent(orderButton, 200, 200, 200))
                .addComponent(recompilationOrder, 200, 400, 400)
                .addComponent(recompilation, 200, 400, 400)
                .addGap(20, 20, 20)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(importFileLabel)
                        .addComponent(inputFile)
                        .addComponent(buildGraph)
                )
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(classToRecompileLabel)
                        .addComponent(chooseClass)
                        .addComponent(orderButton))
                .addGap(20)
                .addComponent(recompilationOrder)
                .addGap(20)
                .addComponent(recompilation)
                .addGap(20)
        );

        //frame for panel
        JFrame panelFrame = new JFrame("Class Dependency Graph");
        panelFrame.add(guiPanel);
        panelFrame.setSize(600, 350);
        panelFrame.setVisible(true);
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        guiPanel.setBorder(padding);
        panelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Refine Panel Elements
        buildGraph.setSize(50, 20);
        orderButton.setSize(50, 20);
        recompilation.setEditable(false);

        //action listener for buildGraph
        buildGraph.addActionListener((ActionEvent f) -> {
            try {
                graph = new directedGraph();
                graph.resetKeys();
                String fileName = Paths.get(System.getProperty("user.dir")) + "\\" + "src" + "\\" + "classdependencygraph" + "\\" + inputFile.getText();
                fileName = fileName.replace("\\", "\\" + "\\");
                BufferedReader in = new BufferedReader(new FileReader(fileName));
                while (in.ready()) {
                    String fileData = in.readLine();
                    String[] lineData = fileData.split(" ");
                    graph.addVertex(graph, lineData[0]);
                    for (int i = 1; i < lineData.length; i++) {
                        stack.push(lineData[i]);
                    }
                    while (!stack.isEmpty()) {
                        vertexNameIndex = graph.addEdge(graph, lineData[0], stack.pop());
                        vertexes = graph.createList(lineData, vertexNameIndex);
                    }
                    graph.listofVertices.add(vertexes);
                    System.out.println(graph.listofVertices);
                }
                in.close();
                JOptionPane.showMessageDialog(null, "Graph Built Successfully");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File Did Not Open");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "IO Exception");
            } catch (StackException ex) {
                try {
                    throw new cycleException();
                } catch (cycleException ex1) {
                    JOptionPane.showMessageDialog(null, "This Graph Contains a Cycle.");
                }
            }
        });//end action listener

        //action listener for buildGraph
        orderButton.addActionListener((ActionEvent f) -> {
            try {
                recompilation.setText("");
                graph.outString = "";
                graph.resetKeyVertexIndex();
                if (chooseClass.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Enter Class Name");
                } else if (!vertexNameIndex.containsKey(chooseClass.getText())) {
                    throw new invalidClassException();
                }
                recompilation.setText(graph.depthFirstSearch(new Vertex(chooseClass.getText()), graph));  
            } catch (cycleException | StackException ex) {
                try {
                    throw new cycleException();
                } catch (cycleException ex1) {
                    JOptionPane.showMessageDialog(null, "This Graph Contains a Cycle");
                }
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "No Graph Has Been Made");
            } catch (invalidClassException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Class");
            }//end try catch
        });//end action listener
    }//end class

    public static void main(String[] args) {
        //run program
        ClassDependencyGraph project4 = new ClassDependencyGraph();
    }

}
