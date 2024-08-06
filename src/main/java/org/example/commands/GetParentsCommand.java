package org.example.commands;

import java.util.List;
import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.example.utility.ValidationUtils;

public class GetParentsCommand implements CommandInterface {
  final ConsoleInputServiceInterface consoleInputService;

  public GetParentsCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public final void execute(final Map<String, Node> nodeDependencies) {

    System.out.println("Enter the nodeId whose parent nodes are needed");
    final String nodeId = consoleInputService.inputNodeId();

    if (ValidationUtils.validateNodeId(nodeId, nodeDependencies)) {
      return;
    }
    final List<String> parents = getParents(nodeDependencies, nodeId);
    printParents(nodeId, parents);
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
