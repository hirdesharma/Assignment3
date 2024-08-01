package org.example.services;

import java.util.Scanner;

public class ConsoleInputService implements ConsoleInputServiceInterface {
  private final Scanner scanner;

  public ConsoleInputService(final Scanner scanner) {
    this.scanner = scanner;
  }

  @Override
  public String inputNodeId() {
    String nodeId = scanner.nextLine();
    return nodeId;
  }

  @Override
  public int inputUserAction() {
    int choice = scanner.nextInt();
    return choice;
  }
}
