package org.example.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DeleteNodeCommand implements CommandInterface {
  @Override
  public void execute(Map<String, ArrayList<String>> childParentDependencies,
                      Map<String, ArrayList<String>> parentChildDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the node id you want to delete with all its dependencies");
    String nodeId = scanner.nextLine();

    if(childParentDependencies.containsKey(nodeId)){
      List<String> childForNode = parentChildDependencies.get(nodeId);
      List<String> parentForNode = childParentDependencies.get(nodeId);
      for(int i=0;i<childForNode.size();++i){
        childParentDependencies.get(childForNode.get(i)).remove(nodeId);
      }
      for (int i=0;i<parentForNode.size();++i){
        parentChildDependencies.get(parentForNode.get(i)).remove(nodeId);
      }
    }
  }
}
