package org.example.commands;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class GetParentsCommand implements CommandInterface {
  ConsoleInputServiceInterface consoleInputService;

  public GetParentsCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter the nodeId whose parent nodes are needed");
    String nodeId = consoleInputService.inputNodeId();

    validateNodeId(nodeId, nodeDependencies);
    List<String> parents = getParents(nodeDependencies, nodeId);
    printParents(nodeId, parents);
  }

  private void validateNodeId(String nodeId, Map<String, Node> nodeDependencies) {
    if (nodeId == null || nodeId.isEmpty() || !nodeDependencies.containsKey(nodeId)) {
      throw new InvalidArgument("There is no node with id: " + nodeId);
    }
  }

  private List<String> getParents(Map<String, Node> nodeDependencies, String nodeId) {
    return nodeDependencies.get(nodeId).getNodeParents();
  }

  private void printParents(String nodeId, List<String> parents) {
    System.out.println("The parent nodes for " + nodeId + " are:");
    for (String parent : parents) {
      System.out.print(parent + " ");
    }
    System.out.println();
  }
}
