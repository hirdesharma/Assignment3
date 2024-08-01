package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.example.validators.CyclicDependencyValidator;

public class AddDependencyCommand implements CommandInterface {
  private final CyclicDependencyValidator cyclicDependencyValidator;
  private final ConsoleInputServiceInterface consoleInputService;

  public AddDependencyCommand(CyclicDependencyValidator cyclicDependencyValidator,
                              ConsoleInputServiceInterface consoleInputService) {
    this.cyclicDependencyValidator = cyclicDependencyValidator;
    this.consoleInputService = consoleInputService;
  }

  @Override
  public void execute(final Map<String, Node> nodeDependencies) {
    System.out.println("give the parent and child node id");
    String parentId = consoleInputService.inputNodeId();
    String childId = consoleInputService.inputNodeId();

    if (validateParentAndChild(childId, parentId, nodeDependencies)
        || cyclicDependencyValidator.checkForCycle(parentId, childId, nodeDependencies)) {
      return;
    }
    updateNodeDependencies(nodeDependencies, parentId, childId);
  }

  private boolean validateParentAndChild(final String childId, final String parentId,
                                         final Map<String, Node> nodeDependencies) {
    if (!nodeDependencies.containsKey(childId) || !nodeDependencies.containsKey(parentId)) {
      System.out.println(
          "There is no node with parentId " + parentId + " or " + "childId " + childId);
      return true;
    }
    return false;
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
