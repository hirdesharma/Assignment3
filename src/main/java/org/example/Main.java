package org.example;

import org.example.commands.AddNodeCommand;
import org.example.commands.CommandInterface;
import org.example.services.UserInputService;
import org.example.services.UserManager;

public class Main {
  public static void main(String[] args) {
    CommandInterface addNodeCommand = new AddNodeCommand();
    UserInputService userInputService = new UserInputService();
    UserManager userManager = new UserManager(userInputService);
    userManager.startManager();
  }
}