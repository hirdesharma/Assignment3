package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class DeleteDependencyCommand implements CommandInterface {
  private final ConsoleInputServiceInterface consoleInputService;

  public DeleteDependencyCommand(final ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Get parent id and child id");

    final String parentId = consoleInputService.inputNodeId();
    final String childId = consoleInputService.inputNodeId();

    validateInput(parentId, childId);
    validateNodesExistence(nodeDependencies, parentId, childId);

    updateParentNode(nodeDependencies, parentId, childId);
    updateChildNode(nodeDependencies, parentId, childId);
  }

  private void validateInput(final String parentId, final String childId) {
    if (parentId == null || childId == null || parentId.isEmpty() || childId.isEmpty()) {
      throw new InvalidArgument("Empty inputs are not allowed");
    }
  }

  private void validateNodesExistence(final Map<String, Node> nodeDependencies,
                                      final String parentId, final String childId) {
    if (!nodeDependencies.containsKey(parentId) || !nodeDependencies.containsKey(childId)) {
      throw new InvalidArgument(
          "There is no node with parentId " + parentId + " or " + "childId " + childId);
    }
  }

  private void updateParentNode(final Map<String, Node> nodeDependencies, final String parentId,
                                final String childId) {
    final ArrayList<String> children = nodeDependencies.get(parentId).getNodeChildren();
    children.remove(childId);
    nodeDependencies.get(parentId).setNodeChildren(children);
  }

  private void updateChildNode(final Map<String, Node> nodeDependencies, final String parentId,
                               final String childId) {
    final ArrayList<String> parents = nodeDependencies.get(childId).getNodeParents();
    parents.remove(parentId);
    nodeDependencies.get(childId).setNodeParents(parents);
  }
}
