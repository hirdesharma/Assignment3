package org.example.commands;

import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class AddNodeCommand implements CommandInterface {
  private final ConsoleInputServiceInterface consoleInputService;

  public AddNodeCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Enter the node key");
    final String nodeId = consoleInputService.inputNodeId();
    if (validateInput(nodeId, nodeDependencies)) {
      return;
    }
    nodeDependencies.put(nodeId, new Node());
    nodeDependencies.get(nodeId).setNodeId(nodeId);
  }

  private boolean validateInput(final String nodeId, final Map<String, Node> nodeDependencies) {
    if (nodeId == null || nodeId.isEmpty() || nodeDependencies.containsKey(nodeId)) {
      System.out.println("Node with id " + nodeId + " already Exist or nodeId is empty");
      return true;
    }
    return false;
  }
}
