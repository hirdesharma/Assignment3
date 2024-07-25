package org.example.commands;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.example.model.Node;

public class DeleteNodeCommand implements CommandInterface {
  @Override
  public void execute(Map<String, Node> nodeDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the node id you want to delete with all its dependencies");
    String nodeId = scanner.nextLine();

    if (nodeDependencies.containsKey(nodeId)) {
      List<String> nodeParents = nodeDependencies.get(nodeId).getNodeParents();
      List<String> nodeChildren = nodeDependencies.get(nodeId).getNodeChildren();
      for (int i = 0; i < nodeChildren.size(); ++i) {
        nodeDependencies.get(nodeChildren.get(i)).getNodeParents().remove(nodeId);
      }
      for (int i = 0; i < nodeParents.size(); ++i) {
        nodeDependencies.get(nodeParents.get(i)).getNodeChildren().remove(nodeId);
      }
    }
  }
}
