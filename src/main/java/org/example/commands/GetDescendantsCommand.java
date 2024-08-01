package org.example.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class GetDescendantsCommand implements CommandInterface {
  ConsoleInputServiceInterface consoleInputService;

  public GetDescendantsCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter the nodeId whose Descendants nodes are needed");
    String nodeId = consoleInputService.inputNodeId();

    validateNodeId(nodeId);
    List<String> descendants = findDescendants(nodeDependencies, nodeId);
    printDescendants(nodeId, descendants);
  }

  private void validateNodeId(String nodeId) {
    if (nodeId == null || nodeId.isEmpty()) {
      throw new IllegalArgumentException("nodeId shouldn't be null or empty");
    }
  }

  private List<String> findDescendants(Map<String, Node> nodeDependencies, String nodeId) {
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

  private void printDescendants(String nodeId, List<String> descendants) {
    System.out.println("The Descendant nodes for " + nodeId + " are:");
    for (int i = 1; i < descendants.size(); ++i) { // Start from 1 to skip the original node
      System.out.print(descendants.get(i) + " ");
    }
    System.out.println();
  }
}
