package org.example.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class GetAncestorsCommand implements CommandInterface {
  ConsoleInputServiceInterface consoleInputService;

  public GetAncestorsCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the nodeId whose Ancestor nodes are needed");
    String nodeId = consoleInputService.inputNodeId();

    validateNodeId(nodeId);
    List<String> ancestors = findAncestors(nodeDependencies, nodeId);
    printAncestors(ancestors);
  }

  private void validateNodeId(String nodeId) {
    if (nodeId == null || nodeId.isEmpty()) {
      throw new IllegalArgumentException("nodeId shouldn't be null or empty");
    }
  }

  private List<String> findAncestors(Map<String, Node> nodeDependencies, String nodeId) {
    Queue<String> nodes = new LinkedList<>();
    List<String> ancestors = new ArrayList<>();

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

  private void printAncestors(List<String> ancestors) {
    System.out.println("The Ancestor nodes are: ");
    for (String ancestor : ancestors) {
      System.out.print(ancestor + " ");
    }
    System.out.println();
  }
}
