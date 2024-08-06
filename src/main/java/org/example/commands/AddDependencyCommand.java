package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.example.utility.ValidationUtils;
import org.example.validators.CyclicDependencyValidator;

public class AddDependencyCommand implements CommandInterface {
  private final ConsoleInputServiceInterface consoleInputService;

  public AddDependencyCommand(
      ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("give the parent and child node id");
    String parentId = consoleInputService.inputNodeId();
    String childId = consoleInputService.inputNodeId();

    if (ValidationUtils.validateParentAndChild(childId, parentId, nodeDependencies)
        || CyclicDependencyValidator.checkForCycle(parentId, childId, nodeDependencies)) {
      return;
    }
    updateNodeDependencies(nodeDependencies, parentId, childId);
  }

  private void updateNodeDependencies(final Map<String, Node> nodeDependencies,
                                      final String parentId,
                                      final String childId) {
    updateParentNode(nodeDependencies, parentId, childId);
    updateChildNode(nodeDependencies, parentId, childId);
  }

  private void updateParentNode(final Map<String, Node> nodeDependencies, final String parentId,
                                final String childId) {
    ArrayList<String> children = nodeDependencies.get(parentId).getNodeChildren();
    if (!children.contains(childId)) {
      children.add(childId);
    }
    nodeDependencies.get(parentId).setNodeChildren(children);
  }

  private void updateChildNode(final Map<String, Node> nodeDependencies, final String parentId,
                               final String childId) {
    ArrayList<String> parents = nodeDependencies.get(childId).getNodeParents();
    if (!parents.contains(parentId)) {
      parents.add(parentId);
    }
    nodeDependencies.get(childId).setNodeParents(parents);
  }
}
