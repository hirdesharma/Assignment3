package org.example.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class GetDescendantsCommand implements CommandInterface {
  private final ConsoleInputServiceInterface consoleInputService;

  public GetDescendantsCommand(final ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Enter the nodeId whose Descendants nodes are needed");
    final String nodeId = consoleInputService.inputNodeId();

    validateNodeId(nodeId);
    final List<String> descendants = findDescendants(nodeDependencies, nodeId);
    printDescendants(nodeId, descendants);
  }

  private void validateNodeId(final String nodeId) {
    if (nodeId == null || nodeId.isEmpty()) {
      throw new IllegalArgumentException("nodeId shouldn't be null or empty");
    }
  }

  private List<String> findDescendants(final Map<String, Node> nodeDependencies,
                                       final String nodeId) {
    final Queue<String> nodes = new LinkedList<>();
    final List<String> descendants = new ArrayList<>();

    nodes.add(nodeId);

    while (!nodes.isEmpty()) {
      final String currentNode = nodes.poll();
      descendants.add(currentNode);

      final List<String> currNodeChildren = nodeDependencies.get(currentNode).getNodeChildren();
      nodes.addAll(currNodeChildren);
    }

    return descendants;
  }

  private void printDescendants(final String nodeId, final List<String> descendants) {
    System.out.println("The Descendant nodes for " + nodeId + " are:");
    for (int i = 1; i < descendants.size(); ++i) { // Start from 1 to skip the original node
      System.out.print(descendants.get(i) + " ");
    }
    System.out.println();
  }
}
