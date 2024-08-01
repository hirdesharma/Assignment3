package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class DeleteDependencyCommand implements CommandInterface {
  ConsoleInputServiceInterface consoleInputService;

  public DeleteDependencyCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Get parent id and child id");

    String parentId = consoleInputService.inputNodeId();
    String childId = consoleInputService.inputNodeId();

    validateInput(parentId, childId);
    validateNodesExistence(nodeDependencies, parentId, childId);

    updateParentNode(nodeDependencies, parentId, childId);
    updateChildNode(nodeDependencies, parentId, childId);
  }

  private void validateInput(String parentId, String childId) {
    if (parentId == null || childId == null || parentId.isEmpty() || childId.isEmpty()) {
      throw new InvalidArgument("Empty inputs are not allowed");
    }
  }

  private void validateNodesExistence(Map<String, Node> nodeDependencies, String parentId,
                                      String childId) {
    if (!nodeDependencies.containsKey(parentId) || !nodeDependencies.containsKey(childId)) {
      throw new InvalidArgument(
          "There is no node with parentId " + parentId + " or " + "childId " + childId);
    }
  }

  private void updateParentNode(Map<String, Node> nodeDependencies, String parentId,
                                String childId) {
    ArrayList<String> children = nodeDependencies.get(parentId).getNodeChildren();
    children.remove(childId);
    nodeDependencies.get(parentId).setNodeChildren(children);
  }

  private void updateChildNode(Map<String, Node> nodeDependencies, String parentId,
                               String childId) {
    ArrayList<String> parents = nodeDependencies.get(childId).getNodeParents();
    parents.remove(parentId);
    nodeDependencies.get(childId).setNodeParents(parents);
  }
}
