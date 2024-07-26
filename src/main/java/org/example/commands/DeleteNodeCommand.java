package org.example.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;

public class DeleteNodeCommand implements CommandInterface {

  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the node id you want to delete with all its dependencies");

    String nodeId = scanner.nextLine();

    if (nodeId.equals("") || nodeId.equals(null)) {
      throw new InvalidArgument("nodeId shouldn't be null or empty");
    }
    // Check if the node exists in the map
    if (nodeDependencies.containsKey(nodeId)) {
      // Retrieve the lists of parent and child nodes
      List<String> nodeParents = nodeDependencies.get(nodeId).getNodeParents();
      List<String> nodeChildren = nodeDependencies.get(nodeId).getNodeChildren();

      // Remove the node ID from each of its children's parent lists
      for (int i = 0; i < nodeChildren.size(); ++i) {
        ArrayList<String> parents = nodeDependencies.get(nodeChildren.get(i)).getNodeParents();
        parents.remove(nodeId);
        nodeDependencies.get(nodeChildren.get(i)).setNodeParents(parents);
      }
      // Remove the node ID from each of its parents' children lists
      for (int i = 0; i < nodeParents.size(); ++i) {
        ArrayList<String> children =
            nodeDependencies.get(nodeParents.get(i)).getNodeChildren();
        children.remove(nodeId);
        nodeDependencies.get(nodeChildren.get(i)).setNodeChildren(children);
      }

      nodeDependencies.remove(nodeId);
      return;
    }

    // If the node does not exist, throw an InvalidArgument exception
    throw new InvalidArgument("There is no node with id " + nodeId);
  }
}
