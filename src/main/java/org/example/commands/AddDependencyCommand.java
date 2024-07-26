package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.validators.CyclicDependencyValidator;

public class AddDependencyCommand implements CommandInterface {

  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("give the parent and child node id");

    String parentId = scanner.nextLine();
    String childId = scanner.nextLine();

    // Check for possibility of any cycle
    CyclicDependencyValidator cyclicDependencyValidator = new CyclicDependencyValidator();
    cyclicDependencyValidator.checkForCycle(parentId, childId, nodeDependencies);

    // Check if both parent and child nodes exist in the map
    if (nodeDependencies.containsKey(parentId) && nodeDependencies.containsKey(childId)) {

      // Update the parent node: remove and add the child ID to ensure it's not duplicated
      ArrayList<String> children =
          nodeDependencies.get(parentId).getNodeChildren();
      children.remove(childId);
      children.add(childId);
      nodeDependencies.get(parentId).setNodeChildren(children);

      // Update the child node: remove and add the parent ID to ensure it's not duplicated
      ArrayList<String> parent = nodeDependencies.get(childId).getNodeParents();
      parent.remove(parentId);
      parent.add(parentId);
      nodeDependencies.get(childId).setNodeParents(parent);


      return; // Exit the method after successful update
    }

    // If either parent or child node does not exist, throw an InvalidArgument exception
    throw new InvalidArgument("There is no node with parentId " + parentId +
        " or " + "childId " + childId);
  }
}
