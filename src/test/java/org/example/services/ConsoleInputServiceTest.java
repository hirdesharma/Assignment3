package org.example.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ConsoleInputServiceTest {

  private ConsoleInputService consoleInputService;
  private Scanner scanner;

  @BeforeEach
  void setUp() {
    scanner = Mockito.mock(Scanner.class);
    consoleInputService = new ConsoleInputService(scanner);
  }

  @Test
  void testInputNodeId() {
    String expectedInput = "testNodeId";
    when(scanner.nextLine()).thenReturn(expectedInput);

    String actualInput = consoleInputService.inputNodeId();
    assertEquals(expectedInput, actualInput);
  }

  @Test
  void testInputNodeIdWithNullInput() {
    String expectedInput = null;
    when(scanner.nextLine()).thenReturn(expectedInput);

    String actualInput = consoleInputService.inputNodeId();
    assertEquals(expectedInput, actualInput);
  }
}
