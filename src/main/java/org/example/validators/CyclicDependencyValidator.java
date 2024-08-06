package org.example.validators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import org.example.model.Node;

public class CyclicDependencyValidator {
  public static boolean checkForCycle(final String parent, final String child,
                                      final Map<String, Node> nodeDependencies) {
    final Queue<String> nodes = new LinkedList<>();
    final ArrayList<String> descendants = new ArrayList<>();

    nodes.add(child);

    // Perform BFS to find all descendant nodes
    while (!nodes.isEmpty()) {
      final String currentNode = nodes.poll(); // Get the next node from the queue
      descendants.add(currentNode); // Add the current node to the list of descendants

      // Get the list of child nodes for the current node
      final ArrayList<String> currNodeChild = nodeDependencies.get(currentNode).getNodeChildren();

      // Add all child nodes to the queue to continue the BFS
      nodes.addAll(currNodeChild);
    }

    if (descendants.contains(parent)) {
      System.out.println("There will be a cycle if these parent child dependency "
          + "establishes");
      return true;
    }
    return false;
  }
}
