package org.example.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class AddNodeCommand implements CommandInterface {
  @Override
  public void execute(Map<String, ArrayList<String>> childParentDependencies,
                      Map<String, ArrayList<String>> parentChildDependencies) {
    System.out.println("Enter the node key");
    Scanner scanner = new Scanner(System.in);

    String newNode = scanner.nextLine();
    childParentDependencies.put(newNode, new ArrayList<>());
  }
}
