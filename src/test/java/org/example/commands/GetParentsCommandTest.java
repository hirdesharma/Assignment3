package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetParentsCommandTest {

  private ConsoleInputServiceInterface consoleInputService;
  private GetParentsCommand getParentsCommand;
  private Map<String, Node> nodeDependencies;
  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final PrintStream originalSystemOut = System.out;

  @BeforeEach
  void setUp() {
    consoleInputService = mock(ConsoleInputServiceInterface.class);
    getParentsCommand = new GetParentsCommand(consoleInputService);
    nodeDependencies = new HashMap<>();

    Node nodeA = new Node();
    Node nodeB = new Node();
    Node nodeC = new Node();
    Node nodeD = new Node();

    nodeA.getNodeParents().add("B");
    nodeA.getNodeParents().add("C");
    nodeB.getNodeParents().add("D");

    nodeDependencies.put("A", nodeA);
    nodeDependencies.put("B", nodeB);
    nodeDependencies.put("C", nodeC);
    nodeDependencies.put("D", nodeD);

    System.setOut(new PrintStream(outputStream));
  }

  @Test
  void testExecuteWithValidNodeId() {
    when(consoleInputService.inputNodeId()).thenReturn("A");

    getParentsCommand.execute(nodeDependencies);

    String actualOutput = outputStream.toString().trim();
    String expectedOutput = "Enter the nodeId whose parent nodes are needed\n" +
        "The parent nodes for A are:\n" +
        "B C";
    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
    System.setOut(originalSystemOut);
  }

  @Test
  void testExecuteWithInvalidNodeId() {
    when(consoleInputService.inputNodeId()).thenReturn("X");

    getParentsCommand.execute(nodeDependencies);

    String actualOutput = outputStream.toString().trim();
    String expectedOutput = "Enter the nodeId whose parent nodes are needed\n" +
        "There is no node with id: X";
    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
    System.setOut(originalSystemOut);
  }

  @Test
  void testExecuteWithEmptyNodeId() {
    when(consoleInputService.inputNodeId()).thenReturn("");

    getParentsCommand.execute(nodeDependencies);

    String actualOutput = outputStream.toString().trim();
    String expectedOutput = "Enter the nodeId whose parent nodes are needed\n" +
        "There is no node with id:";
    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
    System.setOut(originalSystemOut);
  }

  @Test
  void testExecuteWithNullNodeId() {
    when(consoleInputService.inputNodeId()).thenReturn(null);

    getParentsCommand.execute(nodeDependencies);

    String actualOutput = outputStream.toString().trim();
    String expectedOutput = "Enter the nodeId whose parent nodes are needed\n" +
        "There is no node with id: null";
    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
    System.setOut(originalSystemOut);
  }
}
