package org.example.commands;

import java.util.Map;
import java.util.Scanner;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;

public class AddNodeCommand implements CommandInterface {
  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    System.out.println("Enter the node key");
    Scanner scanner = new Scanner(System.in);
    String newNode = scanner.nextLine();
    if(nodeDependencies.containsKey(newNode)){
      throw new InvalidArgument("Node with id "+newNode+" already Exist");
    }
    nodeDependencies.put(newNode,new Node());
    nodeDependencies.get(newNode).setNodeId(newNode);
  }
}
