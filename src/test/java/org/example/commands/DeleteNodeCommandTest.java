package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import org.mockito.Mockito;

class DeleteNodeCommandTest {

  private Map<String, Node> nodeDependencies;
  private DeleteNodeCommand deleteNodeCommand;
  private ConsoleInputServiceInterface consoleInputService;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    nodeDependencies = new HashMap<>();
    consoleInputService = mock(ConsoleInputServiceInterface.class);
    deleteNodeCommand = new DeleteNodeCommand(consoleInputService);
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @Test
  void testDeletesNodeAndItsDependencies() {
    // Create nodes with parent-child relationships
    Node parentNode = new Node();
    Node childNode = new Node();
    Node nodeToDelete = new Node();
    parentNode.setNodeId("parentId");
    childNode.setNodeId("childId");
    nodeToDelete.setNodeId("nodeId");

    // Set up parent-child relationships
    parentNode.getNodeChildren().add("nodeId");
    nodeToDelete.getNodeParents().add("parentId");

    childNode.getNodeParents().add("nodeId");
    nodeToDelete.getNodeChildren().add("childId");

    // Add nodes to the map
    nodeDependencies.put("parentId", parentNode);
    nodeDependencies.put("childId", childNode);
    nodeDependencies.put("nodeId", nodeToDelete);

    // Simulate user input
    when(consoleInputService.inputNodeId()).thenReturn("nodeId");

    deleteNodeCommand.execute(nodeDependencies);

    // Assert that the node has been deleted and dependencies removed
    assertFalse(nodeDependencies.containsKey("nodeId"));
    assertFalse(nodeDependencies.get("parentId").getNodeChildren().contains("nodeId"));
    assertFalse(nodeDependencies.get("childId").getNodeParents().contains("nodeId"));
  }

  @Test
  public void testHandlesNodeNotExist() {
    when(consoleInputService.inputNodeId()).thenReturn("nonExistentNodeId");
    deleteNodeCommand.execute(nodeDependencies);

    String actualOutput = outputStreamCaptor.toString().trim();
    // Assert
    String expectedOutput = "Enter the node id you want to delete with all its dependencies\n" +
        "There is no node with id: nonExistentNodeId";
    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testHandlesNullNodeId() {
    when(consoleInputService.inputNodeId()).thenReturn(null);

    deleteNodeCommand.execute(nodeDependencies);

    String actualOutput = outputStreamCaptor.toString().trim();
    // Assert
    String expectedOutput = "Enter the node id you want to delete with all its dependencies\n" +
        "There is no node with id: null";
    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");
    assertEquals(expectedOutput, actualOutput);
  }
}
