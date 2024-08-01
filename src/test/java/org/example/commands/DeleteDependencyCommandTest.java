package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteDependencyCommandTest {

  private Map<String, Node> nodeDependencies;
  private DeleteDependencyCommand deleteDependencyCommand;
  private ConsoleInputServiceInterface consoleInputService;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    nodeDependencies = new HashMap<>();
    consoleInputService = Mockito.mock(ConsoleInputServiceInterface.class);
    deleteDependencyCommand = new DeleteDependencyCommand(consoleInputService);
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @Test
  void testRemovesDependencySuccessfully() {
    Node parentNode = new Node();
    parentNode.setNodeId("parentId");
    Node childNode = new Node();
    childNode.setNodeId("childId");

    parentNode.getNodeChildren().add("childId");
    childNode.getNodeParents().add("parentId");

    nodeDependencies.put("parentId", parentNode);
    nodeDependencies.put("childId", childNode);

    Mockito.when(consoleInputService.inputNodeId()).thenReturn("parentId", "childId");

    deleteDependencyCommand.execute(nodeDependencies);

    assertFalse(nodeDependencies.get("parentId").getNodeChildren().contains("childId"));
    assertFalse(nodeDependencies.get("childId").getNodeParents().contains("parentId"));
  }

  @Test
  void testThrowsExceptionWhenNodeDoesNotExist() {
    Node existingNode = new Node();
    existingNode.setNodeId("existingNodeId");
    nodeDependencies.put("existingNodeId", existingNode);

    Mockito.when(consoleInputService.inputNodeId()).thenReturn("existingNodeId",
        "nonExistentNodeId");

    deleteDependencyCommand.execute(nodeDependencies);

    String expectedOutput = "Get parent id and child id\nThere is no node with parentId "
        + "existingNodeId or childId nonExistentNodeId";
    String actualOutput = outputStreamCaptor.toString().trim();

    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  void testHandlesNullInput() {
    Mockito.when(consoleInputService.inputNodeId()).thenReturn("", "");

    deleteDependencyCommand.execute(nodeDependencies);

    String expectedOutput = "Get parent id and child id\nEmpty inputs are not allowed";
    String actualOutput = outputStreamCaptor.toString().trim();

    expectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
    actualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

    assertEquals(expectedOutput, actualOutput);
  }
}
