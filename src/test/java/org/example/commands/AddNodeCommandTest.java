package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AddNodeCommandTest {

  private Map<String, Node> nodeDependencies;
  private AddNodeCommand addNodeCommand;
  private ConsoleInputServiceInterface consoleInputService;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    nodeDependencies = new HashMap<>();
    consoleInputService = Mockito.mock(ConsoleInputServiceInterface.class);
    addNodeCommand = new AddNodeCommand(consoleInputService);

    // Setup output capturing
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @Test
  void testAddNodeSuccessfully() {
    // Simulate user input
    String newNodeId = "NodeId";
    Mockito.when(consoleInputService.inputNodeId()).thenReturn(newNodeId);
    addNodeCommand.execute(nodeDependencies);

    assertTrue(nodeDependencies.containsKey(newNodeId));
    assertEquals(newNodeId, nodeDependencies.get(newNodeId).getNodeId());
  }

  @Test
  void testPrintWhenNodeAlreadyExists() {
    // Set up existing node
    Node existingNode = new Node();
    existingNode.setNodeId("existingNodeId");
    nodeDependencies.put("existingNodeId", existingNode);

    // Simulate user input
    Mockito.when(consoleInputService.inputNodeId()).thenReturn("existingNodeId");

    addNodeCommand.execute(nodeDependencies);

    String expectedOutput = "Enter the node key\nNode with id existingNodeId already Exist or "
        + "nodeId is empty";
    String actualOutput = outputStreamCaptor.toString().trim();

    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  void testPrintWhenNodeIdIsEmpty() {
    // Simulate user input with empty string
    Mockito.when(consoleInputService.inputNodeId()).thenReturn("");

    addNodeCommand.execute(nodeDependencies);

    String expectedOutput = "Enter the node key\nNode with id  already Exist or nodeId is empty";
    String actualOutput = outputStreamCaptor.toString().trim();

    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
  }
}
