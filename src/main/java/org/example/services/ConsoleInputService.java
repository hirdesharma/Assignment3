package org.example.services;

import java.util.Scanner;

public class ConsoleInputService implements ConsoleInputServiceInterface {
  Scanner scanner;

  public ConsoleInputService(final Scanner scanner) {
    this.scanner = scanner;
  }

  @Override
  public final String inputNodeId() {
    final String nodeId = scanner.nextLine();
    return nodeId;
  }
}
