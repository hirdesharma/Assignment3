package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
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

class GetDescendantsCommandTest {

  private ConsoleInputServiceInterface consoleInputService;
  private GetDescendantsCommand getDescendantsCommand;
  private Map<String, Node> nodeDependencies;
  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final PrintStream originalSystemOut = System.out;


  @BeforeEach
  void setUp() {
    consoleInputService = Mockito.mock(ConsoleInputServiceInterface.class);
    getDescendantsCommand = new GetDescendantsCommand(consoleInputService);
    nodeDependencies = new HashMap<>();
    System.setOut(new PrintStream(outputStream));
  }

  @Test
  void testExecuteWithMultipleDescendants() {
    when(consoleInputService.inputNodeId()).thenReturn("A");

    nodeDependencies.put("A", new Node());
    nodeDependencies.put("B", new Node());
    nodeDependencies.put("C", new Node());
    nodeDependencies.put("D", new Node());

    nodeDependencies.get("A").setNodeChildren(new ArrayList<>(List.of("B", "C")));
    nodeDependencies.get("B").setNodeChildren(new ArrayList<>(List.of("D")));
    getDescendantsCommand.execute(nodeDependencies);
    String actualOutput = outputStream.toString().trim();
    String expectedOutput = "Enter the nodeId whose Descendants nodes are needed\nThe Descendant "
        + "nodes for A are:\nB C D";

    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);

    System.setOut(originalSystemOut);
  }

  @Test
  void testExecuteWithNoDescendants() {
    when(consoleInputService.inputNodeId()).thenReturn("X");

    Node nodeX = new Node();
    nodeX.setNodeId("X");
    nodeX.setNodeChildren(new ArrayList<>());
    nodeDependencies.put("X", nodeX);
    getDescendantsCommand.execute(nodeDependencies);

    String actualOutput = outputStream.toString().trim();
    String expectedOutput =
        "Enter the nodeId whose Descendants nodes are needed\nThe Descendant nodes "
            + "for X are:";

    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);

    System.setOut(originalSystemOut);
  }

  @Test
  void testExecuteWithInvalidNodeId() {
    when(consoleInputService.inputNodeId()).thenReturn("");
    getDescendantsCommand.execute(nodeDependencies);
    verify(consoleInputService).inputNodeId();
  }
}
