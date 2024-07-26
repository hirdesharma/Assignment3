package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeleteDependencyCommandTest {

  private Map<String, Node> nodeDependencies;
  private DeleteDependencyCommand deleteDependencyCommand;

  @BeforeEach
  void setUp() {
    nodeDependencies = new HashMap<>();
    deleteDependencyCommand = new DeleteDependencyCommand();
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
    System.setIn(new java.io.ByteArrayInputStream("parentId\nchildId\n".getBytes()));

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
    System.setIn(
        new java.io.ByteArrayInputStream("existingNodeId\nnonExistentNodeId\n".getBytes()));

    InvalidArgument exception = assertThrows(InvalidArgument.class, () -> {
      deleteDependencyCommand.execute(nodeDependencies);
    });

    assertEquals("There is no node with parentId existingNodeId or childId nonExistentNodeId",
        exception.getMessage());
  }

  @Test
  void execute_handlesNullInput() {
    // Simulate user input with null (empty strings)
    System.setIn(new java.io.ByteArrayInputStream("\n\n".getBytes()));

    assertThrows(InvalidArgument.class, () ->
        deleteDependencyCommand.execute(nodeDependencies));
  }
}
