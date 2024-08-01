package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AddNodeCommandTest {

  private Map<String, Node> nodeDependencies;
  private AddNodeCommand addNodeCommand;
  private ConsoleInputServiceInterface consoleInputService;

  @BeforeEach
  void setUp() {
    nodeDependencies = new HashMap<>();
    consoleInputService = Mockito.mock(ConsoleInputServiceInterface.class);
    addNodeCommand = new AddNodeCommand(consoleInputService);
  }

  @Test
  void execute_addsNodeSuccessfully() {
    // Simulate user input
    when(consoleInputService.inputNodeId()).thenReturn("newNodeId");

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
    when(consoleInputService.inputNodeId()).thenReturn("existingNodeId");

    assertThrows(InvalidArgument.class, () ->
        addNodeCommand.execute(nodeDependencies));
  }

  @Test
  void execute_handlesNullInput() {
    // Simulate user input with null (empty string)
    when(consoleInputService.inputNodeId()).thenReturn("");

    assertThrows(InvalidArgument.class, () -> {
      addNodeCommand.execute(nodeDependencies);
    });
  }
}
