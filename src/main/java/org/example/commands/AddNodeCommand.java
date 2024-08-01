package org.example.commands;

import java.util.Map;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class AddNodeCommand implements CommandInterface {
  private final ConsoleInputServiceInterface consoleInputService;

  public AddNodeCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    System.out.println("Enter the node key");
    String nodeId = consoleInputService.inputNodeId();

    validateInput(nodeId, nodeDependencies);

    nodeDependencies.put(nodeId, new Node());
    nodeDependencies.get(nodeId).setNodeId(nodeId);
  }

  private void validateInput(String nodeId, Map<String, Node> nodeDependencies) {
    if (nodeId == null || nodeId.isEmpty() || nodeDependencies.containsKey(nodeId)) {
      throw new InvalidArgument("Node with id " + nodeId + " already Exist or nodeId is empty");
    }
  }
}
