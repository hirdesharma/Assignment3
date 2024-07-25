package org.example.services;

import java.util.Scanner;

public class UserInputService {
  public int getUserInput() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the operation Number you want to perform");
    String operations = """
        1 = Get the immediate parents of a node, passing the node id as input parameter.
        2 = Get the immediate children of a node, passing the node id as input parameter.
        3 = Get the ancestors of a node, passing the node id as input parameter.
        4 = Get the descendants of a node, passing the node id as input parameter.
        5 = Delete dependency from a tree, passing parent node id and child node id.
        6 = Delete a node from a tree, passing node id as input parameter. This should delete all the\s
        dependencies of the node.
        7 = Add a new dependency to a tree, passing parent node id and child node id. This should check for\s
        cyclic dependencies.
        8 = Add a new node to tree. This node will have no parents and children. Dependency will be\s
        established by calling the 7 number API""";
    System.out.println(operations);

    return scanner.nextInt();
  }
}
