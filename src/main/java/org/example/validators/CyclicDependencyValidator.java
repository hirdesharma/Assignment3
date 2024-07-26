package org.example.validators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;

public class CyclicDependencyValidator {
  public void checkForCycle(String parent, String child, Map<String, Node> nodeDependencies) {
    Queue<String> nodes = new LinkedList<>();
    ArrayList<String> descendants = new ArrayList<>();

    nodes.add(child);

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

    if (descendants.contains(parent)) {
      throw new InvalidArgument("There will be a cycle if these parent child dependency "
          + "establishes");
    }
  }
}
