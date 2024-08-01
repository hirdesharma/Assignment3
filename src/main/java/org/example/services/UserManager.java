package org.example.services;

import java.util.HashMap;
import java.util.Map;
import org.example.commands.AddDependencyCommand;
import org.example.commands.AddNodeCommand;
import org.example.commands.CommandInterface;
import org.example.commands.DeleteDependencyCommand;
import org.example.commands.DeleteNodeCommand;
import org.example.commands.GetAncestorsCommand;
import org.example.commands.GetChildrenCommand;
import org.example.commands.GetDescendantsCommand;
import org.example.commands.GetParentsCommand;
import org.example.exceptions.InvalidArgument;
import org.example.model.Node;
import org.example.validators.CyclicDependencyValidator;

public class UserManager {
  private final CyclicDependencyValidator cyclicDependencyValidator;
  private final ConsoleInputServiceInterface consoleInputService;
  private final Map<Integer, CommandInterface> commandMap;
  private final Map<String, Node> nodeDependencies;

  private final UserInputServiceInterface userInputService;

  public UserManager(final UserInputServiceInterface userInputService,
                     final CyclicDependencyValidator cyclicDependencyValidator,
                     final ConsoleInputServiceInterface consoleInputService) {
    this.nodeDependencies = new HashMap<>();
    this.commandMap = new HashMap<>();
    this.cyclicDependencyValidator = cyclicDependencyValidator;
    this.userInputService = userInputService;
    this.consoleInputService = consoleInputService;
    this.commandMap.put(1,
        new AddDependencyCommand(cyclicDependencyValidator, consoleInputService));
    this.commandMap.put(2, new AddNodeCommand(consoleInputService));
    this.commandMap.put(3, new DeleteDependencyCommand(consoleInputService));
    this.commandMap.put(4, new DeleteNodeCommand(consoleInputService));
    this.commandMap.put(5, new GetAncestorsCommand(consoleInputService));
    this.commandMap.put(6, new GetChildrenCommand(consoleInputService));
    this.commandMap.put(7, new GetDescendantsCommand(consoleInputService));
    this.commandMap.put(8, new GetParentsCommand(consoleInputService));
  }

  public void startManager() {
    boolean terminate = false;
    while (!terminate) {
      try {
        final int userChoice = userInputService.getUserInput();
        final CommandInterface commandInterface = commandMap.get(userChoice);
        if (commandInterface != null) {
          commandInterface.execute(nodeDependencies);
        } else {
          throw new InvalidArgument("Command not found for choice: " + userChoice);
        }
      } catch (Exception e) {
        System.err.println("Invalid Command: " + e.getMessage());
      }
    }
  }
}
