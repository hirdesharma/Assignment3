package org.example.commands;

import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.example.utility.ValidationUtils;

public class AddNodeCommand implements CommandInterface {
  private final ConsoleInputServiceInterface consoleInputService;

  public AddNodeCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public final void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Enter the node key");
    final String nodeId = consoleInputService.inputNodeId();
    if (ValidationUtils.validateInput(nodeId, nodeDependencies)) {
      return;
    }
    nodeDependencies.put(nodeId, new Node());
    nodeDependencies.get(nodeId).setNodeId(nodeId);
  }
}
