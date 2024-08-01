package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.example.validators.CyclicDependencyValidator;

public class AddDependencyCommand implements CommandInterface {
  private final CyclicDependencyValidator cyclicDependencyValidator;
  private final ConsoleInputServiceInterface consoleInputService;

  public AddDependencyCommand(final CyclicDependencyValidator cyclicDependencyValidator,
                              final ConsoleInputServiceInterface consoleInputService) {
    this.cyclicDependencyValidator = cyclicDependencyValidator;
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("give the parent and child node id");

    final String parentId = consoleInputService.inputNodeId();
    final String childId = consoleInputService.inputNodeId();

    validateParentAndChild(childId, parentId, nodeDependencies);

    cyclicDependencyValidator.checkForCycle(parentId, childId, nodeDependencies);
    updateNodeDependencies(nodeDependencies, parentId, childId);
  }

  private void validateParentAndChild(final String childId, final String parentId,
                                      final Map<String, Node> nodeDependencies) {
    if (!nodeDependencies.containsKey(childId) || !nodeDependencies.containsKey(parentId)) {
      throw new InvalidArgument(
          "There is no node with parentId " + parentId + " or " + "childId " + childId);
    }
  }

  private void updateNodeDependencies(final Map<String, Node> nodeDependencies,
                                      final String parentId,
                                      final String childId) {
    updateParentNode(nodeDependencies, parentId, childId);
    updateChildNode(nodeDependencies, parentId, childId);
  }

  private void updateParentNode(final Map<String, Node> nodeDependencies, final String parentId,
                                final String childId) {
    final ArrayList<String> children = nodeDependencies.get(parentId).getNodeChildren();
    if (!children.contains(childId)) {
      children.add(childId);
    }
    nodeDependencies.get(parentId).setNodeChildren(children);
  }

  private void updateChildNode(final Map<String, Node> nodeDependencies, final String parentId,
                               final String childId) {
    final ArrayList<String> parents = nodeDependencies.get(childId).getNodeParents();
    if (!parents.contains(parentId)) {
      parents.add(parentId);
    }
    nodeDependencies.get(childId).setNodeParents(parents);
  }
}
