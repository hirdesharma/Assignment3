package org.example.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import org.example.model.Node;

public class GetDescendantsCommand implements CommandInterface{
  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);
    String nodeId = scanner.nextLine();
    System.out.println("Enter the nodeId whose Descendants nodes are needed");

    System.out.println("The Descendants nodes for " + nodeId + " are");

    Queue<String> nodes = new LinkedList<>();
    ArrayList<String> descendants = new ArrayList<>();
    nodes.add(nodeId);

    while (nodes.peek() != null) {
      String currentNode = nodes.poll();
      descendants.add(currentNode);
      ArrayList<String> currNodeChild = nodeDependencies.get(currentNode).getNodeChildren();
      for (int i = 0; i < currNodeChild.size(); ++i) {
        nodes.add(currNodeChild.get(i));
      }
    }
    for (int i = 0; i < descendants.size(); ++i) {
      System.out.print(descendants.get(i) + " ");
    }
    System.out.println();
  }
}
