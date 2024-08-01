package org.example.services;

public class UserInputService implements UserInputServiceInterface {
  ConsoleInputServiceInterface consoleInputService;

  public UserInputService(ConsoleInputServiceInterface consoleInputService) {
    this.consoleInputService = consoleInputService;
  }

  @Override
  public int getUserInput() {
    System.out.println("Enter the operation Number you want to perform");
    System.out.println("1 = Add a new dependency to a tree.");
    System.out.println("2 = Add a new node to tree.");
    System.out.println("3 = Delete dependency from a tree.");
    System.out.println("4 = Delete a node from a tree.");
    System.out.println("5 = Get the ancestors of a node.");
    System.out.println("6 = Get the immediate children of a node.");
    System.out.println("7 = Get the descendants of a node.");
    System.out.println("8 = Get the immediate parents of a node.");

    return consoleInputService.inputUserAction();
  }
}
