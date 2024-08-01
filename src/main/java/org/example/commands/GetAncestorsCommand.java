package org.example.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class GetAncestorsCommand implements CommandInterface {
  private final ConsoleInputServiceInterface consoleInputService;

  public GetAncestorsCommand(final ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Enter the nodeId whose Ancestor nodes are needed");
    final String nodeId = consoleInputService.inputNodeId();

    validateNodeId(nodeId);
    final List<String> ancestors = findAncestors(nodeDependencies, nodeId);
    printAncestors(ancestors);
  }

  private void validateNodeId(final String nodeId) {
    if (nodeId == null || nodeId.isEmpty()) {
      throw new IllegalArgumentException("nodeId shouldn't be null or empty");
    }
  }

  private List<String> findAncestors(final Map<String, Node> nodeDependencies,
                                     final String nodeId) {
    final Queue<String> nodes = new LinkedList<>();
    final List<String> ancestors = new ArrayList<>();

    nodes.add(nodeId);

    while (!nodes.isEmpty()) {
      final String currentNode = nodes.poll();
      ancestors.add(currentNode);

      final List<String> currNodeParents = nodeDependencies.get(currentNode).getNodeParents();

      for (final String parent : currNodeParents) {
        nodes.add(parent);
      }
    }

    return ancestors;
  }

  private void printAncestors(final List<String> ancestors) {
    System.out.println("The Ancestor nodes are: ");
    for (final String ancestor : ancestors) {
      System.out.print(ancestor + " ");
    }
    System.out.println();
  }
}
