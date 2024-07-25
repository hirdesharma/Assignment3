package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import org.example.model.Node;

public class GetParentsCommand implements CommandInterface {
  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the nodeId whose parent nodes are needed");
    String nodeId = scanner.nextLine();

    System.out.println("The parent nodes for " + nodeId + " are");
    ArrayList<String> parents = nodeDependencies.get(nodeId).getNodeParents();
    for (int i = 0; i < parents.size(); ++i) {
      System.out.print(parents.get(i) + " ");
    }
    System.out.println();
  }
}
