package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class AddDependencyCommand implements CommandInterface{
  @Override
  public void execute(Map<String, ArrayList<String>> childParentDependencies,
                      Map<String, ArrayList<String>> parentChildDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("give the parent and child node id");
    String parentId = scanner.nextLine();
    String childId = scanner.nextLine();

    if(childParentDependencies.containsKey(childId)){
      ArrayList<String> parentForChildId = childParentDependencies.get(childId);
      parentForChildId.remove(parentId);
      parentForChildId.add(parentId);
      childParentDependencies.put(childId,parentForChildId);

      ArrayList<String> childForParentId = parentChildDependencies.get(parentId);
      childForParentId.remove(childId);
      childForParentId.add(childId);
      parentChildDependencies.put(parentId,childForParentId);
    }
  }
}
