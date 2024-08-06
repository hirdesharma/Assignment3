package org.example.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.example.utility.ValidationUtils;

public class DeleteNodeCommand implements CommandInterface {
  final ConsoleInputServiceInterface consoleInputService;

  public DeleteNodeCommand(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public final void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("Enter the node id you want to delete with all its dependencies");

    final String nodeId = consoleInputService.inputNodeId();

    if (ValidationUtils.validateNodeId(nodeId, nodeDependencies)) {
      return;
    }
    deleteNodeWithDependencies(nodeDependencies, nodeId);
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
    for (String childId : nodeChildren) {
      final ArrayList<String> parents = nodeDependencies.get(childId).getNodeParents();
      parents.remove(nodeId);
      nodeDependencies.get(childId).setNodeParents(parents);
    }
  }

  private void removeNodeDependencyFromParent(final Map<String, Node> nodeDependencies,
                                              final String nodeId,
                                              final List<String> nodeParents) {
    for (String parentId : nodeParents) {
      final ArrayList<String> children = nodeDependencies.get(parentId).getNodeChildren();
      children.remove(nodeId);
      nodeDependencies.get(parentId).setNodeChildren(children);
    }
  }
}
