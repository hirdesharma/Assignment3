package org.example.commands;

import java.util.Map;
import java.util.Scanner;
import org.example.model.Node;

public class DeleteDependencyCommand implements CommandInterface {
  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Get parent id and child id");
    String parentId = scanner.nextLine();
    String childId = scanner.nextLine();

    if(nodeDependencies.containsKey(parentId)){
      nodeDependencies.get(parentId).getNodeChildren().remove(childId);
      nodeDependencies.get(childId).getNodeParents().remove(parentId);
    }
  }
}
