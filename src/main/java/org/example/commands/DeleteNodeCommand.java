package org.example.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;

public class DeleteNodeCommand implements CommandInterface {
  private final ConsoleInputServiceInterface consoleInputService;

  public DeleteNodeCommand(final ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Enter the node id you want to delete with all its dependencies");

    final String nodeId = consoleInputService.inputNodeId();

    validateNodeId(nodeId);
    deleteNodeWithDependencies(nodeDependencies, nodeId);
  }

  private void validateNodeId(final String nodeId) {
    if (nodeId == null || nodeId.isEmpty()) {
      throw new InvalidArgument("nodeId shouldn't be null or empty");
    }
  }

  private void deleteNodeWithDependencies(final Map<String, Node> nodeDependencies,
                                          final String nodeId) {
    if (nodeDependencies.containsKey(nodeId)) {
      final List<String> nodeParents = nodeDependencies.get(nodeId).getNodeParents();
      final List<String> nodeChildren = nodeDependencies.get(nodeId).getNodeChildren();

      removeNodeDependencyFromChildren(nodeDependencies, nodeId, nodeChildren);
      removeNodeDependencyFromParent(nodeDependencies, nodeId, nodeParents);

      nodeDependencies.remove(nodeId);
    } else {
      throw new InvalidArgument("There is no node with id " + nodeId);
    }
  }

  private void removeNodeDependencyFromChildren(final Map<String, Node> nodeDependencies,
                                                final String nodeId,
                                                final List<String> nodeChildren) {
    for (final String childId : nodeChildren) {
      final ArrayList<String> parents = nodeDependencies.get(childId).getNodeParents();
      parents.remove(nodeId);
      nodeDependencies.get(childId).setNodeParents(parents);
    }
  }

  private void removeNodeDependencyFromParent(final Map<String, Node> nodeDependencies,
                                              final String nodeId, final List<String> nodeParents) {
    for (final String parentId : nodeParents) {
      final ArrayList<String> children = nodeDependencies.get(parentId).getNodeChildren();
      children.remove(nodeId);
      nodeDependencies.get(parentId).setNodeChildren(children);
    }
  }
}
