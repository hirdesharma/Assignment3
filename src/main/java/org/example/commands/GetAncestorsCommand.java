package org.example.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class GetAncestorsCommand implements CommandInterface {
  ConsoleInputServiceInterface consoleInputService;

  public GetAncestorsCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Enter the nodeId whose Ancestor nodes are needed");
    String nodeId = consoleInputService.inputNodeId();

    if (validateNodeId(nodeId, nodeDependencies)) {
      return;
    }
    List<String> ancestors = findAncestors(nodeDependencies, nodeId);
    printAncestors(ancestors);
  }

  private boolean validateNodeId(final String nodeId, final Map<String, Node> nodeDependencies) {
    if (nodeId == null || nodeId.isEmpty() || !nodeDependencies.containsKey(nodeId)) {
      System.out.println("there is no node with id : " + nodeId);
      return true;
    }
    return false;
  }

  private List<String> findAncestors(final Map<String, Node> nodeDependencies,
                                     final String nodeId) {
    final Queue<String> nodes = new LinkedList<>();
    final List<String> ancestors = new ArrayList<>();

    nodes.add(nodeId);

    while (!nodes.isEmpty()) {
      String currentNode = nodes.poll();
      ancestors.add(currentNode);

      List<String> currNodeParents = nodeDependencies.get(currentNode).getNodeParents();

      for (String parent : currNodeParents) {
        nodes.add(parent);
      }
    }

    return ancestors;
  }

  private void printAncestors(final List<String> ancestors) {
    System.out.println("The Ancestor nodes are: ");
    for (int i = 1; i < ancestors.size(); ++i) {
      System.out.print(ancestors.get(i) + " ");
    }
    System.out.println();
  }
}
