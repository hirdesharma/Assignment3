package org.example.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.example.commands.AddNodeCommand;
import org.example.commands.CommandInterface;

public class UserManager {
  Map<String, ArrayList<String>> childParentDependencies;
  Map<String, ArrayList<String>> parentChildDependencies;
  Map<Integer,CommandInterface>commandMap;

  CommandInterface addNodeCommand;

  public UserManager(CommandInterface addNodeCommand) {
    childParentDependencies = new HashMap<>();
    parentChildDependencies = new HashMap<>();
    this.addNodeCommand=addNodeCommand;
    commandMap.put(1,new AddNodeCommand());
  }

  public void startManager(){

  }
}
