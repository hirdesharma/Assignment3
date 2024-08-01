package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class DeleteDependencyCommand implements CommandInterface {
  ConsoleInputServiceInterface consoleInputService;

  public DeleteDependencyCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Get parent id and child id");

    String parentId = consoleInputService.inputNodeId();
    String childId = consoleInputService.inputNodeId();

    if (validateInput(parentId, childId) || validateNodesExistence(nodeDependencies, parentId,
        childId)) {
      return;
    }

    updateParentNode(nodeDependencies, parentId, childId);
    updateChildNode(nodeDependencies, parentId, childId);
  }

  private boolean validateInput(final String parentId, final String childId) {
    if (parentId == null || childId == null || parentId.isEmpty() || childId.isEmpty()) {
      System.out.println("Empty inputs are not allowed");
      return true;
    }
    return false;
  }

  private boolean validateNodesExistence(final Map<String, Node> nodeDependencies,
                                         final String parentId,
                                         final String childId) {
    if (!nodeDependencies.containsKey(parentId) || !nodeDependencies.containsKey(childId)) {
      System.out.println(
          "There is no node with parentId " + parentId + " or " + "childId " + childId);
      return true;
    }
    return false;
  }

  private void updateParentNode(final Map<String, Node> nodeDependencies, final String parentId,
                                String childId) {
    ArrayList<String> children = nodeDependencies.get(parentId).getNodeChildren();
    children.remove(childId);
    nodeDependencies.get(parentId).setNodeChildren(children);
  }

  private void updateChildNode(final Map<String, Node> nodeDependencies, final String parentId,
                               String childId) {
    ArrayList<String> parents = nodeDependencies.get(childId).getNodeParents();
    parents.remove(parentId);
    nodeDependencies.get(childId).setNodeParents(parents);
  }
}
