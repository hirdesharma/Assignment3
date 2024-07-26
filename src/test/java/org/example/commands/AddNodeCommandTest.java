package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddNodeCommandTest {

  private Map<String, Node> nodeDependencies;
  private AddNodeCommand addNodeCommand;

  @BeforeEach
  void setUp() {
    nodeDependencies = new HashMap<>();
    addNodeCommand = new AddNodeCommand();
  }

  @Test
  void execute_addsNodeSuccessfully() {
    // Simulate user input
    System.setIn(new java.io.ByteArrayInputStream("newNodeId\n".getBytes()));

    addNodeCommand.execute(nodeDependencies);

    assertTrue(nodeDependencies.containsKey("newNodeId"));
    assertEquals("newNodeId", nodeDependencies.get("newNodeId").getNodeId());
  }

  @Test
  void execute_throwsExceptionWhenNodeAlreadyExists() {
    // Set up existing node
    Node existingNode = new Node();
    existingNode.setNodeId("existingNodeId");
    nodeDependencies.put("existingNodeId", existingNode);

    // Simulate user input
    System.setIn(new java.io.ByteArrayInputStream("existingNodeId\n".getBytes()));

    assertThrows(InvalidArgument.class, () ->
        addNodeCommand.execute(nodeDependencies));
  }

  @Test
  void execute_handlesNullInput() {
    // Simulate user input with null (empty string)
    System.setIn(new java.io.ByteArrayInputStream("\n".getBytes()));

    assertThrows(InvalidArgument.class, () -> {
      addNodeCommand.execute(nodeDependencies);
    });
  }
}
