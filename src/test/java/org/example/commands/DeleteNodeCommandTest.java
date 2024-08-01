package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteNodeCommandTest {

  private Map<String, Node> nodeDependencies;
  private DeleteNodeCommand deleteNodeCommand;
  private ConsoleInputServiceInterface consoleInputService;

  @BeforeEach
  void setUp() {
    nodeDependencies = new HashMap<>();
    consoleInputService = Mockito.mock(ConsoleInputServiceInterface.class);
    deleteNodeCommand = new DeleteNodeCommand(consoleInputService);
  }

  @Test
  void execute_deletesNodeAndItsDependencies() {
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
  void execute_throwsExceptionWhenNodeDoesNotExist() {
    // Add a node to the map
    Node existingNode = new Node();
    existingNode.setNodeId("existingNodeId");
    nodeDependencies.put("existingNodeId", existingNode);

    // Simulate user input with a non-existent node
    when(consoleInputService.inputNodeId()).thenReturn("nonExistentNodeId");

    InvalidArgument exception = assertThrows(InvalidArgument.class, () -> {
      deleteNodeCommand.execute(nodeDependencies);
    });

    assertEquals("There is no node with id nonExistentNodeId", exception.getMessage());
  }

  @Test
  void execute_handlesEmptyNodeId() {
    // Add a node to the map
    Node existingNode = new Node();
    existingNode.setNodeId("existingNodeId");
    nodeDependencies.put("existingNodeId", existingNode);

    // Simulate user input with an empty node ID
    when(consoleInputService.inputNodeId()).thenReturn("");

    assertThrows(InvalidArgument.class, () -> deleteNodeCommand.execute(nodeDependencies));
  }

  @Test
  void execute_handlesNullNodeId() {
    // Simulate user input with a null (empty string)
    when(consoleInputService.inputNodeId()).thenReturn("null");

    InvalidArgument exception = assertThrows(InvalidArgument.class, () -> {
      deleteNodeCommand.execute(nodeDependencies);
    });

    assertEquals("There is no node with id null", exception.getMessage());
  }
}
