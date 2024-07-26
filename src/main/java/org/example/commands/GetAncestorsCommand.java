package org.example.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import org.example.model.Node;

public class GetAncestorsCommand implements CommandInterface {

  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the nodeId whose Ancestors nodes are needed");

    String nodeId = scanner.nextLine();
    System.out.println("The Ancestor nodes for " + nodeId + " are");

    // Initialize a queue to perform breadth-first search (BFS) and a list to store ancestor nodes
    Queue<String> nodes = new LinkedList<>();
    ArrayList<String> ancestors = new ArrayList<>();

    nodes.add(nodeId);

    // Perform BFS to find all ancestor nodes
    while (nodes.peek() != null) {
      String currentNode = nodes.poll(); // Get the next node from the queue
      ancestors.add(currentNode); // Add the current node to the list of ancestors

      // Get the list of parent nodes for the current node
      ArrayList<String> currNodeParents = nodeDependencies.get(currentNode).getNodeParents();

      // Add all parent nodes to the queue to continue the BFS
      for (int i = 1; i < currNodeParents.size(); ++i) {
        nodes.add(currNodeParents.get(i));
      }
    }

    // Print all ancestor nodes
    for (int i = 0; i < ancestors.size(); ++i) {
      System.out.print(ancestors.get(i) + " ");
    }
    System.out.println();
  }
}
