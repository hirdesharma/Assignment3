package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import org.example.model.Node;

public class GetChildrenCommand implements CommandInterface {

  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the nodeId whose child nodes are needed");

    String nodeId = scanner.nextLine();
    System.out.println("The child nodes for " + nodeId + " are");

    // Retrieve the list of child nodes for the given node ID
    ArrayList<String> children = nodeDependencies.get(nodeId).getNodeChildren();

    // Print all child nodes
    for (int i = 0; i < children.size(); ++i) {
      System.out.print(children.get(i) + " ");
    }
    System.out.println();
  }
}
