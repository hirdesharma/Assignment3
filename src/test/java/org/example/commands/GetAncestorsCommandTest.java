package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetAncestorsCommandTest {

  private Map<String, Node> nodeDependencies;
  private GetAncestorsCommand getAncestorsCommand;
  private ConsoleInputServiceInterface consoleInputService;
  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final PrintStream originalSystemOut = System.out;

  @BeforeEach
  void setUp() {
    nodeDependencies = new HashMap<>();
    consoleInputService = Mockito.mock(ConsoleInputServiceInterface.class);
    getAncestorsCommand = new GetAncestorsCommand(consoleInputService);

    nodeDependencies.put("A", new Node());
    nodeDependencies.put("B", new Node());
    nodeDependencies.put("C", new Node());
    nodeDependencies.put("D", new Node());

    // Setting up relationships
    nodeDependencies.get("B").getNodeParents().add("A");
    nodeDependencies.get("C").getNodeParents().add("B");
    nodeDependencies.get("D").getNodeParents().add("C");

    System.setOut(new PrintStream(outputStream));
  }

  @Test
  void testExecuteWithValidNodeId() {
    when(consoleInputService.inputNodeId()).thenReturn("D");
    getAncestorsCommand.execute(nodeDependencies);

    String actualOutput = outputStream.toString().trim();
    String expectedOutput = "Enter the nodeId whose Ancestor nodes are needed\nThe Ancestor nodes "
        + "are: \nC B A";
    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
    System.setOut(originalSystemOut);
  }

  @Test
  void testExecuteWithNullNodeId() {
    when(consoleInputService.inputNodeId()).thenReturn(null);

    getAncestorsCommand.execute(nodeDependencies);

    String actualOutput = outputStream.toString().trim();
    String expectedOutput = "Enter the nodeId whose Ancestor nodes are needed\n" +
        "there is no node with id : null";
    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
    System.setOut(originalSystemOut);
  }

}
