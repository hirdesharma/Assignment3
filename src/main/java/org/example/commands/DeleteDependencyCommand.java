package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;

public class DeleteDependencyCommand implements CommandInterface {

  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Get parent id and child id");

    String parentId = scanner.nextLine();
    String childId = scanner.nextLine();

    if (parentId.equals("") || childId.equals("") || childId.equals(null) || parentId.equals(
        null)) {
      throw new InvalidArgument("Empty inputs are not allowed");
    }

    // Check if both parent and child nodes exist in the map
    if (nodeDependencies.containsKey(parentId) && nodeDependencies.containsKey(childId)) {

      // Update the parent node: remove the child id
      ArrayList<String> children =
          nodeDependencies.get(parentId).getNodeChildren();
      children.remove(childId);
      nodeDependencies.get(parentId).setNodeChildren(children);

      // Update the child node: remove the parent id
      ArrayList<String> parent = nodeDependencies.get(childId).getNodeParents();
      parent.remove(parentId);
      nodeDependencies.get(childId).setNodeParents(parent);

      return; // Exit the method after successful update
    }

    // If either parent or child node does not exist, throw an InvalidArgument exception
    throw new InvalidArgument("There is no node with parentId " + parentId +
        " or " + "childId " + childId);
  }
}
