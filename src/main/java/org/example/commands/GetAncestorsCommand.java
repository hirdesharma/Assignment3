package org.example.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class GetAncestorsCommand implements CommandInterface {
  @Override
  public void execute(Map<String, ArrayList<String>> childParentDependencies,
                      Map<String, ArrayList<String>> parentChildDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the nodeId whose Ancestors nodes are needed");
    String nodeId = scanner.nextLine();

    System.out.println("The Ancestor nodes for " + nodeId + " are");

    Queue<String> nodes = new LinkedList<>();
    ArrayList<String> ancestors = new ArrayList<>();
    nodes.add(nodeId);

    while (nodes.peek() != null) {
      String currentNode = nodes.poll();
      ancestors.add(currentNode);
      ArrayList<String> currNodeParents = childParentDependencies.get(currentNode);
      for (int i = 0; i < currNodeParents.size(); ++i) {
        nodes.add(currNodeParents.get(i));
      }
    }

    for (int i = 0; i < ancestors.size(); ++i) {
      System.out.print(ancestors.get(i) + " ");
    }
    System.out.println();
  }
}
