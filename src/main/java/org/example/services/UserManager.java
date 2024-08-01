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
  CyclicDependencyValidator cyclicDependencyValidator;
  ConsoleInputServiceInterface consoleInputService;
  Map<Integer, CommandInterface> commandMap;
  Map<String, Node> nodeDependencies;

  UserInputServiceInterface userInputService;

  public UserManager(UserInputServiceInterface userInputService,
                     CyclicDependencyValidator cyclicDependencyValidator,
                     ConsoleInputServiceInterface consoleInputService) {
    nodeDependencies = new HashMap<>();
    commandMap = new HashMap<>();
    this.cyclicDependencyValidator = cyclicDependencyValidator;
    this.userInputService = userInputService;
    this.consoleInputService = consoleInputService;
    commandMap.put(1, new AddDependencyCommand(cyclicDependencyValidator, consoleInputService));
    commandMap.put(2, new AddNodeCommand(consoleInputService));
    commandMap.put(3, new DeleteDependencyCommand(consoleInputService));
    commandMap.put(4, new DeleteNodeCommand(consoleInputService));
    commandMap.put(5, new GetAncestorsCommand(consoleInputService));
    commandMap.put(6, new GetChildrenCommand(consoleInputService));
    commandMap.put(7, new GetDescendantsCommand(consoleInputService));
    commandMap.put(8, new GetParentsCommand(consoleInputService));
  }

  public void startManager() {
    boolean terminate = false;
    while (!terminate) {
      try {
        int userChoice = userInputService.getUserInput();
        CommandInterface commandInterface = commandMap.get(userChoice);
        commandInterface.execute(nodeDependencies);
      } catch (Exception e) {
        throw new InvalidArgument("Invalid Command : " + e.getMessage());
      }
    }
  }
}
