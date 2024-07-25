package org.example.commands;

import java.util.Map;
import java.util.Scanner;
import org.example.model.Node;

public class AddDependencyCommand implements CommandInterface{
  @Override
  public void execute(Map<String, Node>nodeDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("give the parent and child node id");
    String parentId = scanner.nextLine();
    String childId = scanner.nextLine();

    if(nodeDependencies.containsKey(parentId)){
      nodeDependencies.get(parentId).getNodeChildren().remove(childId);
      nodeDependencies.get(parentId).getNodeChildren().add(childId);
      nodeDependencies.get(childId).getNodeParents().remove(parentId);
      nodeDependencies.get(childId).getNodeParents().add(parentId);
    }
  }
}
