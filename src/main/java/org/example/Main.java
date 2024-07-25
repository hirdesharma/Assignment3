package org.example;

import org.example.commands.AddNodeCommand;
import org.example.commands.CommandInterface;
import org.example.services.UserManager;

public class Main {
  public static void main(String[] args) {
    CommandInterface addNodeCommand = new AddNodeCommand();
    UserManager userManager = new UserManager(addNodeCommand);
    userManager.startManager();
  }
}