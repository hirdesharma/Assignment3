package org.example.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.example.utility.ValidationUtils;

public class GetChildrenCommand implements CommandInterface {
  final ConsoleInputServiceInterface consoleInputService;

  public GetChildrenCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Enter the nodeId whose child nodes are needed");

    final String nodeId = consoleInputService.inputNodeId();

    if (ValidationUtils.validateNodeId(nodeId, nodeDependencies)) {
      return;
    }
    // Retrieve the list of child nodes for the given node ID
    final ArrayList<String> children = nodeDependencies.get(nodeId).getNodeChildren();
    printChildren(children, nodeId);
  }

  private void printChildren(final List<String> children, final String nodeId) {
    System.out.println("The child nodes for " + nodeId + " are");
    // Print all child nodes
    for (int i = 0; i < children.size(); ++i) {
      System.out.print(children.get(i) + " ");
    }
    System.out.println();
  }
}
