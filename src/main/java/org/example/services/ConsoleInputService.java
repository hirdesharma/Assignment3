package org.example.services;

import java.util.Scanner;

public class ConsoleInputService implements ConsoleInputServiceInterface {
  Scanner scanner;

  public ConsoleInputService(Scanner scanner) {
    this.scanner = scanner;
  }

  @Override
  public String inputNodeId() {
    final String nodeId = scanner.nextLine();
    return nodeId;
  }
}
