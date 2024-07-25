package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class DeleteDependencyCommand implements CommandInterface {
  @Override
  public void execute(Map<String, ArrayList<String>> childParentDependencies,
                      Map<String, ArrayList<String>> parentChildDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Get parent id and child id");
    String parentId = scanner.nextLine();
    String childId = scanner.nextLine();

    if (childParentDependencies.containsKey(childId)) {
      childParentDependencies.get(childId).remove(parentId);
      parentChildDependencies.get(parentId).remove(childId);

    }
  }
}
