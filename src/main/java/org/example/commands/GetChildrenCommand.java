package org.example.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class GetChildrenCommand implements CommandInterface {
  ConsoleInputServiceInterface consoleInputService;

  public GetChildrenCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the nodeId whose child nodes are needed");

    String nodeId = consoleInputService.inputNodeId();

    validateNodeId(nodeId, nodeDependencies);
    // Retrieve the list of child nodes for the given node ID
    ArrayList<String> children = nodeDependencies.get(nodeId).getNodeChildren();
    printChildren(children, nodeId);
  }

  private void validateNodeId(String nodeId, Map<String, Node> nodeDependencies) {
    if (nodeId == null || nodeId.isEmpty() || !nodeDependencies.containsKey(nodeId)) {
      throw new InvalidArgument("There is no node with id: " + nodeId);
    }
  }

  private void printChildren(List<String> children, String nodeId) {
    System.out.println("The child nodes for " + nodeId + " are");
    // Print all child nodes
    for (int i = 0; i < children.size(); ++i) {
      System.out.print(children.get(i) + " ");
    }
    System.out.println();
  }
}
