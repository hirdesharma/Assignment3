package org.example.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class GetDescendantsCommand implements CommandInterface {
  ConsoleInputServiceInterface consoleInputService;

  public GetDescendantsCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Enter the nodeId whose Descendants nodes are needed");
    String nodeId = consoleInputService.inputNodeId();

    if (validateNodeId(nodeId)) {
      return;
    }
    List<String> descendants = findDescendants(nodeDependencies, nodeId);
    printDescendants(nodeId, descendants);
  }

  private boolean validateNodeId(final String nodeId) {
    if (nodeId == null || nodeId.isEmpty()) {
      System.out.println("nodeId shouldn't be null or empty");
      return true;
    }
    return false;
  }

  private List<String> findDescendants(final Map<String, Node> nodeDependencies,
                                       final String nodeId) {
    Queue<String> nodes = new LinkedList<>();
    List<String> descendants = new ArrayList<>();

    nodes.add(nodeId);

    while (!nodes.isEmpty()) {
      String currentNode = nodes.poll();
      descendants.add(currentNode);

      List<String> currNodeChildren = nodeDependencies.get(currentNode).getNodeChildren();
      nodes.addAll(currNodeChildren);
    }

    return descendants;
  }

  private void printDescendants(final String nodeId, List<String> descendants) {
    System.out.println("The Descendant nodes for " + nodeId + " are:");
    for (int i = 1; i < descendants.size(); ++i) { // Start from 1 to skip the original node
      System.out.print(descendants.get(i) + " ");
    }
    System.out.println();
  }
}
