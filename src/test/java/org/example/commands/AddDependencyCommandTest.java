package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.example.validators.CyclicDependencyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AddDependencyCommandTest {

  private Map<String, Node> nodeDependencies;
  private AddDependencyCommand addDependencyCommand;
  private CyclicDependencyValidator cyclicDependencyValidator;
  private ConsoleInputServiceInterface consoleInputService;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    nodeDependencies = new HashMap<>();
    cyclicDependencyValidator = new CyclicDependencyValidator();
    consoleInputService = Mockito.mock(ConsoleInputServiceInterface.class);
    addDependencyCommand = new AddDependencyCommand(consoleInputService);

    Node parentNode = new Node();
    parentNode.setNodeId("parent");

    Node childNode = new Node();
    childNode.setNodeId("child");

    nodeDependencies.put("parent", parentNode);
    nodeDependencies.put("child", childNode);

    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @Test
  void execute_addDependencySuccessfully() {
    Mockito.when(consoleInputService.inputNodeId()).thenReturn("parent", "child");

    addDependencyCommand.execute(nodeDependencies);

    assertTrue(nodeDependencies.get("parent").getNodeChildren().contains("child"));
    assertTrue(nodeDependencies.get("child").getNodeParents().contains("parent"));
  }

  @Test
  void testPrintWhenParentNotFound() {
    Mockito.when(consoleInputService.inputNodeId()).thenReturn("nonexistent", "child");

    addDependencyCommand.execute(nodeDependencies);

    String expectedOutput = "give the parent and child node id\nThere is no node with parentId "
        + "nonexistent or childId child";
    String actualOutput = outputStreamCaptor.toString().trim();

    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  void testPrintWhenChildNotFound() {
    Mockito.when(consoleInputService.inputNodeId()).thenReturn("parent", "nonexistent");

    addDependencyCommand.execute(nodeDependencies);

    String expectedOutput = "give the parent and child node id\nThere is no node with parentId "
        + "parent or childId nonexistent";
    String actualOutput = outputStreamCaptor.toString().trim();

    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");


    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  void execute_handlesDuplicateDependencies() {
    Mockito.when(consoleInputService.inputNodeId()).thenReturn("parent", "child");

    addDependencyCommand.execute(nodeDependencies); // First addition

    Mockito.when(consoleInputService.inputNodeId()).thenReturn("parent", "child");
    addDependencyCommand.execute(nodeDependencies); // Second addition (duplicate)

    assertEquals(1, nodeDependencies.get("parent").getNodeChildren().size());
    assertEquals(1, nodeDependencies.get("child").getNodeParents().size());
  }
}
