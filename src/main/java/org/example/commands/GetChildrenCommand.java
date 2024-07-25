package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class GetChildrenCommand implements CommandInterface {
  @Override
  public void execute(Map<String, ArrayList<String>> childParentDependencies,
                      Map<String, ArrayList<String>> parentChildDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the nodeId whose child nodes are needed");
    String nodeId = scanner.nextLine();

    System.out.println("The child nodes for " + nodeId + " are");
    ArrayList<String> children = parentChildDependencies.get(nodeId);
    for (int i = 0; i < children.size(); ++i) {
      System.out.print(children.get(i) + " ");
    }
    System.out.println();
  }
}
