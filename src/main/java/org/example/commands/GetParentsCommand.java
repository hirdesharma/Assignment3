package org.example.commands;

import java.util.List;
import java.util.Map;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class GetParentsCommand implements CommandInterface {
  private final ConsoleInputServiceInterface consoleInputService;

  public GetParentsCommand(final ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Enter the nodeId whose parent nodes are needed");
    final String nodeId = consoleInputService.inputNodeId();

    validateNodeId(nodeId, nodeDependencies);
    final List<String> parents = getParents(nodeDependencies, nodeId);
    printParents(nodeId, parents);
  }

  private void validateNodeId(final String nodeId, final Map<String, Node> nodeDependencies) {
    if (nodeId == null || nodeId.isEmpty() || !nodeDependencies.containsKey(nodeId)) {
      throw new InvalidArgument("There is no node with id: " + nodeId);
    }
  }

  private List<String> getParents(final Map<String, Node> nodeDependencies, final String nodeId) {
    return nodeDependencies.get(nodeId).getNodeParents();
  }

  private void printParents(final String nodeId, final List<String> parents) {
    System.out.println("The parent nodes for " + nodeId + " are:");
    for (final String parent : parents) {
      System.out.print(parent + " ");
    }
    System.out.println();
  }
}
