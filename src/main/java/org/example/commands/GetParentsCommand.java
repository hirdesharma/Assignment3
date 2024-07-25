package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class GetParentsCommand implements CommandInterface{
  @Override
  public void execute(Map<String, ArrayList<String>> childParentDependencies,
                      Map<String, ArrayList<String>> parentChildDependencies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the nodeId whose parent nodes are needed");
    String nodeId = scanner.nextLine();

    System.out.println("The parent nodes for "+ nodeId + " are");
    ArrayList<String >parents = childParentDependencies.get(nodeId);
    for (int i=0;i<parents.size();++i){
      System.out.print(parents.get(i) + " ");
    }
    System.out.println();
  }
}
