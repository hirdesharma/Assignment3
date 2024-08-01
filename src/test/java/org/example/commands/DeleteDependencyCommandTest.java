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

class DeleteDependencyCommandTest {

  private Map<String, Node> nodeDependencies;
  private DeleteDependencyCommand deleteDependencyCommand;
  private ConsoleInputServiceInterface consoleInputService;

  @BeforeEach
  void setUp() {
    nodeDependencies = new HashMap<>();
    consoleInputService = Mockito.mock(ConsoleInputServiceInterface.class);
    deleteDependencyCommand = new DeleteDependencyCommand(consoleInputService);
  }

  @Test
  void execute_removesDependencySuccessfully() {
    // Set up nodes with a parent-child relationship
    Node parentNode = new Node();
    Node childNode = new Node();
    parentNode.setNodeId("parentId");
    childNode.setNodeId("childId");

    parentNode.getNodeChildren().add("childId");
    childNode.getNodeParents().add("parentId");

    nodeDependencies.put("parentId", parentNode);
    nodeDependencies.put("childId", childNode);

    // Simulate user input
    when(consoleInputService.inputNodeId()).thenReturn("parentId", "childId");

    deleteDependencyCommand.execute(nodeDependencies);

    assertFalse(nodeDependencies.get("parentId").getNodeChildren().contains("childId"));
    assertFalse(nodeDependencies.get("childId").getNodeParents().contains("parentId"));
  }

  @Test
  void execute_throwsExceptionWhenNodeDoesNotExist() {
    // Set up nodes
    Node existingNode = new Node();
    existingNode.setNodeId("existingNodeId");
    nodeDependencies.put("existingNodeId", existingNode);

    // Simulate user input with a non-existent node
    when(consoleInputService.inputNodeId()).thenReturn("existingNodeId", "nonExistentNodeId");

    InvalidArgument exception = assertThrows(InvalidArgument.class, () -> {
      deleteDependencyCommand.execute(nodeDependencies);
    });

    assertEquals("There is no node with parentId existingNodeId or childId nonExistentNodeId",
        exception.getMessage());
  }

  @Test
  void execute_handlesNullInput() {
    // Simulate user input with null (empty strings)
    when(consoleInputService.inputNodeId()).thenReturn("", "");

    assertThrows(InvalidArgument.class, () -> {
      deleteDependencyCommand.execute(nodeDependencies);
    });
  }
}
