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

class AddDependencyCommandTest {

  private Map<String, Node> nodeDependencies;
  private CommandInterface addDependencyCommand;

  @BeforeEach
  void setUp() {
    nodeDependencies = new HashMap<>();
    addDependencyCommand = new AddDependencyCommand();

    // Setting up test data
    Node parentNode = new Node();
    parentNode.setNodeId("parent");

    Node childNode = new Node();
    childNode.setNodeId("child");

    nodeDependencies.put("parent", parentNode);
    nodeDependencies.put("child", childNode);
  }

  @Test
  void execute_addDependencySuccessfully() {
    // Simulate user input
    System.setIn(new java.io.ByteArrayInputStream("parent\nchild\n".getBytes()));

    addDependencyCommand.execute(nodeDependencies);

    assertTrue(nodeDependencies.get("parent").getNodeChildren().contains("child"));
    assertTrue(nodeDependencies.get("child").getNodeParents().contains("parent"));
  }

  @Test
  void execute_throwsExceptionWhenParentNotFound() {
    // Simulate user input
    System.setIn(new java.io.ByteArrayInputStream("nonexistent\nchild\n".getBytes()));

    assertThrows(InvalidArgument.class, () ->
        addDependencyCommand.execute(nodeDependencies)
    );
  }

  @Test
  void execute_throwsExceptionWhenChildNotFound() {
    // Simulate user input
    System.setIn(new java.io.ByteArrayInputStream("parent\nnonexistent\n".getBytes()));

    assertThrows(InvalidArgument.class, () ->
        addDependencyCommand.execute(nodeDependencies));
  }

  @Test
  void execute_handlesDuplicateDependencies() {
    // Simulate user input
    System.setIn(new java.io.ByteArrayInputStream("parent\nchild\n".getBytes()));
    addDependencyCommand.execute(nodeDependencies); // First addition
    System.setIn(new java.io.ByteArrayInputStream("parent\nchild\n".getBytes()));
    addDependencyCommand.execute(nodeDependencies); // Second addition (duplicate)

    assertEquals(1, nodeDependencies.get("parent").getNodeChildren().size());
    assertEquals(1, nodeDependencies.get("child").getNodeParents().size());
  }
}
