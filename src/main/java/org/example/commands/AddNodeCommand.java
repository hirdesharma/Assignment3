package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import org.example.model.Node;

public class AddNodeCommand implements CommandInterface {
  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    System.out.println("Enter the node key");
    Scanner scanner = new Scanner(System.in);
    String newNode = scanner.nextLine();
    nodeDependencies.put(newNode,new Node());
  }
}
