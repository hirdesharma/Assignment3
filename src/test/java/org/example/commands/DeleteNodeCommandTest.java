package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeleteNodeCommandTest {

  private Map<String, Node> nodeDependencies;
  private DeleteNodeCommand deleteNodeCommand;

  @BeforeEach
  void setUp() {
    nodeDependencies = new HashMap<>();
    deleteNodeCommand = new DeleteNodeCommand();
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
    ArrayList<String> newChild = parentNode.getNodeChildren();
    newChild.add("nodeId");
    parentNode.setNodeChildren(newChild);

    ArrayList<String> newNodeParent = nodeToDelete.getNodeParents();
    newNodeParent.add("parentId");
    nodeToDelete.setNodeParents(newNodeParent);

    ArrayList<String> newParent = parentNode.getNodeParents();
    newParent.add("nodeId");
    parentNode.setNodeParents(newParent);

    ArrayList<String> newNodeChild = nodeToDelete.getNodeChildren();
    newNodeChild.add("childId");
    nodeToDelete.setNodeChildren(newNodeChild);

    // Add nodes to the map
    nodeDependencies.put("parentId", parentNode);
    nodeDependencies.put("childId", childNode);
    nodeDependencies.put("nodeId", nodeToDelete);

    // Simulate user input
    System.setIn(new java.io.ByteArrayInputStream("nodeId\n".getBytes()));

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
    System.setIn(new java.io.ByteArrayInputStream("nonExistentNodeId\n".getBytes()));

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
    System.setIn(new java.io.ByteArrayInputStream("\n".getBytes()));

    assertThrows(InvalidArgument.class, () ->
        deleteNodeCommand.execute(nodeDependencies));
  }

  @Test
  void execute_handlesNullNodeId() {
    // Simulate user input with null (empty string)
    System.setIn(new java.io.ByteArrayInputStream("null\n".getBytes()));

    InvalidArgument exception = assertThrows(InvalidArgument.class, () -> {
      deleteNodeCommand.execute(nodeDependencies);
    });

    assertEquals("There is no node with id null", exception.getMessage());
  }
}
