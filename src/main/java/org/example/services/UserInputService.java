package org.example.services;

import java.util.Scanner;

public class UserInputService implements UserInputServiceInterface{
  public int getUserInput() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the operation Number you want to perform");
    String operations = """
        1 = Add a new dependency to a tree.
        2 = Add a new node to tree.
        3 = Delete dependency from a tree.
        4 = Delete a node from a tree.
        5 = Get the ancestors of a node.
        6 = Get the immediate children of a node.
        7 = Get the descendants of a node.
        8 = Get the immediate parents of a node.""";
    System.out.println(operations);

    return scanner.nextInt();
  }
}
