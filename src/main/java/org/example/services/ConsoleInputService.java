package org.example.services;

import java.util.Scanner;

public class ConsoleInputService implements ConsoleInputServiceInterface {
  Scanner scanner;

  public ConsoleInputService(Scanner scanner) {
    this.scanner = scanner;
  }

  @Override
  public String inputNodeId() {
    String nodeId = scanner.nextLine();
    scanner.close();
    return nodeId;
  }

  @Override
  public int inputUserAction() {
    int choice = scanner.nextInt();
    scanner.close();
    return choice;
  }
}
