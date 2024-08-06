package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetChildrenCommandTest {

  private ConsoleInputServiceInterface consoleInputService;
  private GetChildrenCommand getChildrenCommand;
  private Map<String, Node> nodeDependencies;
  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final PrintStream originalSystemOut = System.out;

  @BeforeEach
  void setUp() {
    consoleInputService = Mockito.mock(ConsoleInputServiceInterface.class);
    getChildrenCommand = new GetChildrenCommand(consoleInputService);
    nodeDependencies = new HashMap<>();

    nodeDependencies.put("A", new Node());
    nodeDependencies.put("B", new Node());
    nodeDependencies.put("C", new Node());
    nodeDependencies.put("D", new Node());
    ArrayList<String> childrenOfA = new ArrayList<>(List.of("B", "C", "D"));
    nodeDependencies.get("A").setNodeChildren(childrenOfA);

    System.setOut(new PrintStream(outputStream));
  }

  @Test
  void testInvalidNodeId() {
    when(consoleInputService.inputNodeId()).thenReturn("Z");
    getChildrenCommand.execute(nodeDependencies);

    String actualOutput = outputStream.toString().trim();
    String expectedOutput = "Enter the nodeId whose child nodes are needed\nThere is no node with "
        + "id: Z";

    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
    assertEquals(expectedOutput, actualOutput);
    System.setOut(originalSystemOut);
  }

  @Test
  void testExecuteWithNullNodeId() {
    when(consoleInputService.inputNodeId()).thenReturn(null);
    getChildrenCommand.execute(nodeDependencies);

    String actualOutput = outputStream.toString().trim();
    String expectedOutput = "Enter the nodeId whose child nodes are needed\nThere is no node with "
        + "id: null";

    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
    assertEquals(expectedOutput, actualOutput);
    System.setOut(originalSystemOut);
  }

  @Test
  void testExecuteWithMultipleChildren() {
    when(consoleInputService.inputNodeId()).thenReturn("A");
    getChildrenCommand.execute(nodeDependencies);

    String actualOutput = outputStream.toString().trim();
    String expectedOutput = "Enter the nodeId whose child nodes are needed\nThe child nodes for A "
        + "are\nB C D";

    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");
    assertEquals(expectedOutput, actualOutput);

    System.setOut(originalSystemOut);
  }
}
