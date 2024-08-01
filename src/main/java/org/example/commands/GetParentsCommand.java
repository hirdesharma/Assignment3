package org.example.commands;

import java.util.List;
import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class GetParentsCommand implements CommandInterface {
  ConsoleInputServiceInterface consoleInputService;

  public GetParentsCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {

    System.out.println("Enter the nodeId whose parent nodes are needed");
    String nodeId = consoleInputService.inputNodeId();

    if (validateNodeId(nodeId, nodeDependencies)) {
      return;
    }
    List<String> parents = getParents(nodeDependencies, nodeId);
    printParents(nodeId, parents);
  }

  private boolean validateNodeId(final String nodeId, final Map<String, Node> nodeDependencies) {
    if (nodeId == null || nodeId.isEmpty() || !nodeDependencies.containsKey(nodeId)) {
      System.out.println("There is no node with id: " + nodeId);
      return true;
    }
    return false;
  }

  private List<String> getParents(final Map<String, Node> nodeDependencies, final String nodeId) {
    return nodeDependencies.get(nodeId).getNodeParents();
  }

  private void printParents(final String nodeId, final List<String> parents) {
    System.out.println("The parent nodes for " + nodeId + " are:");
    for (int i = 0; i < parents.size(); ++i) {
      System.out.print(parents.get(i) + " ");
    }
    System.out.println();
  }
}
