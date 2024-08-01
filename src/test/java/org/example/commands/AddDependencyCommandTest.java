package org.example.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.services.ConsoleInputServiceInterface;
import org.example.validators.CyclicDependencyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AddDependencyCommandTest {

  private Map<String, Node> nodeDependencies;
  private CommandInterface addDependencyCommand;
  private CyclicDependencyValidator cyclicDependencyValidator;
  private ConsoleInputServiceInterface consoleInputService;

  @BeforeEach
  void setUp() {
    nodeDependencies = new HashMap<>();
    cyclicDependencyValidator = new CyclicDependencyValidator();
    consoleInputService = Mockito.mock(ConsoleInputServiceInterface.class);
    addDependencyCommand = new AddDependencyCommand(cyclicDependencyValidator, consoleInputService);

    // Setting up test data
    Node parentNode = new Node();
    parentNode.setNodeId("parent");

    Node childNode = new Node();
    childNode.setNodeId("child");

    nodeDependencies.put("parent", parentNode);
    nodeDependencies.put("child", childNode);
  }

  @IgnoreForBinding
  void execute_addDependencySuccessfully() {
    Mockito.when(consoleInputService.inputNodeId())
        .thenReturn("parent")
        .thenReturn("child");

    addDependencyCommand.execute(nodeDependencies);

    assertTrue(nodeDependencies.get("parent").getNodeChildren().contains("child"));
    assertTrue(nodeDependencies.get("child").getNodeParents().contains("parent"));
  }

  @Test
  void execute_throwsExceptionWhenParentNotFound() {
    Mockito.when(consoleInputService.inputNodeId()).thenReturn("nonexistent", "child");

    assertThrows(InvalidArgument.class, () ->
        addDependencyCommand.execute(nodeDependencies)
    );
  }

  @Test
  void execute_throwsExceptionWhenChildNotFound() {
    Mockito.when(consoleInputService.inputNodeId()).thenReturn("parent", "nonexistent");

    assertThrows(InvalidArgument.class, () ->
        addDependencyCommand.execute(nodeDependencies));
  }

  @IgnoreForBinding
  void execute_handlesDuplicateDependencies() {
    Mockito.when(consoleInputService.inputNodeId()).thenReturn("parent", "child");

    addDependencyCommand.execute(nodeDependencies); // First addition

    Mockito.when(consoleInputService.inputNodeId()).thenReturn("parent", "child");
    addDependencyCommand.execute(nodeDependencies); // Second addition (duplicate)

    assertEquals(1, nodeDependencies.get("parent").getNodeChildren().size());
    assertEquals(1, nodeDependencies.get("child").getNodeParents().size());
  }
}
