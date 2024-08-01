package org.example.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class DeleteNodeCommand implements CommandInterface {
  ConsoleInputServiceInterface consoleInputService;

  public DeleteNodeCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the node id you want to delete with all its dependencies");

    String nodeId = consoleInputService.inputNodeId();

    validateNodeId(nodeId);
    deleteNodeWithDependencies(nodeDependencies, nodeId);
  }

  private void validateNodeId(String nodeId) {
    if (nodeId == null || nodeId.isEmpty()) {
      throw new InvalidArgument("nodeId shouldn't be null or empty");
    }
  }

  private void deleteNodeWithDependencies(Map<String, Node> nodeDependencies, String nodeId) {
    if (nodeDependencies.containsKey(nodeId)) {
      List<String> nodeParents = nodeDependencies.get(nodeId).getNodeParents();
      List<String> nodeChildren = nodeDependencies.get(nodeId).getNodeChildren();

      removeNodeDependencyFromChildren(nodeDependencies, nodeId, nodeChildren);
      removeNodeDependencyFromParent(nodeDependencies, nodeId, nodeParents);

      nodeDependencies.remove(nodeId);
    } else {
      throw new InvalidArgument("There is no node with id " + nodeId);
    }
  }

  private void removeNodeDependencyFromChildren(Map<String, Node> nodeDependencies, String nodeId,
                                                List<String> nodeChildren) {
    for (String childId : nodeChildren) {
      ArrayList<String> parents = nodeDependencies.get(childId).getNodeParents();
      parents.remove(nodeId);
      nodeDependencies.get(childId).setNodeParents(parents);
    }
  }

  private void removeNodeDependencyFromParent(Map<String, Node> nodeDependencies, String nodeId,
                                              List<String> nodeParents) {
    for (String parentId : nodeParents) {
      ArrayList<String> children = nodeDependencies.get(parentId).getNodeChildren();
      children.remove(nodeId);
      nodeDependencies.get(parentId).setNodeChildren(children);
    }
  }
}
