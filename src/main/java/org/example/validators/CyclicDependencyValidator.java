package org.example.validators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;

public class CyclicDependencyValidator {

  public void checkForCycle(final String parent, final String child,
                            final Map<String, Node> nodeDependencies) {
    // Validate input
    if (parent == null || child == null || nodeDependencies == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }

    // Initialize BFS queue and list for storing descendants
    final Queue<String> nodes = new LinkedList<>();
    final ArrayList<String> descendants = new ArrayList<>();

    // Start BFS from the child node
    nodes.add(child);

    while (!nodes.isEmpty()) {
      final String currentNode = nodes.poll();
      descendants.add(currentNode);

      // Get the list of children for the current node
      final ArrayList<String> currNodeChildren = nodeDependencies.get(currentNode)
          .getNodeChildren();

      // Add all children to the BFS queue
      nodes.addAll(currNodeChildren);
    }

    // Check if the parent node is in the descendants list
    if (descendants.contains(parent)) {
      throw new InvalidArgument("Adding this dependency would create a cycle between parent "
          + parent + " and child " + child);
    }
  }
}
