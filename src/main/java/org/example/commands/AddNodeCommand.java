package org.example.commands;

import java.util.Map;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class AddNodeCommand implements CommandInterface {
  private final ConsoleInputServiceInterface consoleInputService;

  public AddNodeCommand(final ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Enter the node key");
    final String nodeId = consoleInputService.inputNodeId();

    validateInput(nodeId, nodeDependencies);

    nodeDependencies.put(nodeId, new Node());
    nodeDependencies.get(nodeId).setNodeId(nodeId);
  }

  private void validateInput(final String nodeId, final Map<String, Node> nodeDependencies) {
    if (nodeId == null || nodeId.isEmpty() || nodeDependencies.containsKey(nodeId)) {
      throw new InvalidArgument("Node with id " + nodeId + " already exists or nodeId is empty");
    }
  }
}
