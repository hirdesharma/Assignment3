package org.example.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
  final CyclicDependencyValidator cyclicDependencyValidator;
  final ConsoleInputServiceInterface consoleInputService;
  final UserInputServiceInterface userInputService;
  final Map<Integer, CommandInterface> commandMap;
  final Map<String, Node> nodeDependencies;


  public UserManager(final UserInputServiceInterface userInputService,
                     final CyclicDependencyValidator cyclicDependencyValidator,
                     final ConsoleInputServiceInterface consoleInputService) {
    nodeDependencies = new HashMap<>();
    commandMap = new HashMap<>();
    this.cyclicDependencyValidator = cyclicDependencyValidator;
    this.userInputService = userInputService;
    this.consoleInputService = consoleInputService;
    commandMap.put(1, new AddDependencyCommand(consoleInputService));
    commandMap.put(2, new AddNodeCommand(consoleInputService));
    commandMap.put(3, new DeleteDependencyCommand(consoleInputService));
    commandMap.put(4, new DeleteNodeCommand(consoleInputService));
    commandMap.put(5, new GetAncestorsCommand(consoleInputService));
    commandMap.put(6, new GetChildrenCommand(consoleInputService));
    commandMap.put(7, new GetDescendantsCommand(consoleInputService));
    commandMap.put(8, new GetParentsCommand(consoleInputService));
  }

  public final void startManager() {
    final boolean terminate = false;
    while (!terminate) {
      try {
        final int userChoice = userInputService.getUserInput();
        final CommandInterface commandInterface = commandMap.get(userChoice);
        if (Objects.isNull(commandInterface)) {
          System.out.println("Choice should be a valid integer between 1 and 8");
        } else {
          commandInterface.execute(nodeDependencies);
        }
      } catch (Exception e) {
        throw new InvalidArgument("Invalid Command : " + e.getMessage());
      }
    }
  }
}
