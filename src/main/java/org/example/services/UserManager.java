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
import org.example.model.Node;

public class UserManager {
  Map<Integer, CommandInterface> commandMap;
  Map<String, Node> nodeDependencies;

  UserInputService userInputService;

  public UserManager(UserInputService userInputService) {
    nodeDependencies = new HashMap<>();
    this.userInputService = userInputService;
    commandMap.put(1, new AddDependencyCommand());
    commandMap.put(2, new AddNodeCommand());
    commandMap.put(3, new DeleteDependencyCommand());
    commandMap.put(4, new DeleteNodeCommand());
    commandMap.put(5, new GetAncestorsCommand());
    commandMap.put(6, new GetChildrenCommand());
    commandMap.put(7, new GetDescendantsCommand());
    commandMap.put(8, new GetParentsCommand());
  }

  public void startManager() {
    int userChoice = userInputService.getUserInput();
    CommandInterface commandInterface = commandMap.get(userChoice);
    commandInterface.execute(nodeDependencies);
  }
}
