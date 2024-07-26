package org.example.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import org.example.model.Node;

public class GetDescendantsCommand implements CommandInterface {

  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter the nodeId whose Descendants nodes are needed");
    String nodeId = scanner.nextLine();
    System.out.println("The Descendants nodes for " + nodeId + " are");

    // Initialize a queue to perform breadth-first search (BFS) and a list to store descendant nodes
    Queue<String> nodes = new LinkedList<>();
    ArrayList<String> descendants = new ArrayList<>();

    nodes.add(nodeId);

    // Perform BFS to find all descendant nodes
    while (nodes.peek() != null) {
      String currentNode = nodes.poll(); // Get the next node from the queue
      descendants.add(currentNode); // Add the current node to the list of descendants

      // Get the list of child nodes for the current node
      ArrayList<String> currNodeChild = nodeDependencies.get(currentNode).getNodeChildren();

      // Add all child nodes to the queue to continue the BFS
      for (int i = 0; i < currNodeChild.size(); ++i) {
        nodes.add(currNodeChild.get(i));
      }
    }

    // Print all descendant nodes
    for (int i = 0; i < descendants.size(); ++i) {
      System.out.print(descendants.get(i) + " ");
    }
    System.out.println();
  }
}
