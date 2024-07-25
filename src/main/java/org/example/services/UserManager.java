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

  public UserManager() {
    childParentDependencies = new HashMap<>();
    parentChildDependencies = new HashMap<>();
    commandMap.put(1,new AddNodeCommand());
  }
}
