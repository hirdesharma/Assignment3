package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.example.utility.ValidationUtils;

public class DeleteDependencyCommand implements CommandInterface {
  final ConsoleInputServiceInterface consoleInputService;

  public DeleteDependencyCommand(final ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Get parent id and child id");

    final String parentId = consoleInputService.inputNodeId();
    final String childId = consoleInputService.inputNodeId();

    if (validateInput(parentId, childId) || ValidationUtils.validateParentAndChild(childId,
        parentId,
        nodeDependencies)) {
      return;
    }

    updateParentNode(nodeDependencies, parentId, childId);
    updateChildNode(nodeDependencies, parentId, childId);
  }

  private boolean validateInput(final String parentId, final String childId) {
    // Validate that both parentId and childId are non-null and non-empty
    if (Objects.nonNull(parentId) && !parentId.isEmpty() && Objects.nonNull(childId)
        && !childId.isEmpty()) {
      return false;
    }
    // If validation fails, print an error and return false
    System.out.println("Invalid input: Both parentId and childId must be non-null and non-empty.");
    return true;
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
