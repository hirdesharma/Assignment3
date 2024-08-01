package org.example.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserInputServiceTest {

  private UserInputService userInputService;
  private ConsoleInputServiceInterface consoleInputService;
  private Scanner scanner;

  @BeforeEach
  void setUp() {
    consoleInputService = Mockito.mock(ConsoleInputServiceInterface.class);
    scanner = Mockito.mock(Scanner.class);
    userInputService = new UserInputService(consoleInputService);
  }

  @Test
  void testGetUserInput() {
    // Simulate user input
    int expectedInput = 1;
    InputStream input = new ByteArrayInputStream(String.valueOf(expectedInput).getBytes());
    System.setIn(input);
    scanner = new Scanner(System.in);
    userInputService = new UserInputService(consoleInputService);

    int actualInput = userInputService.getUserInput();

    assertEquals(expectedInput, actualInput);
  }
}
