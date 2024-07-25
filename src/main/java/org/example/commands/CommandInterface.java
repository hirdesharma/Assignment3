package org.example.commands;

import java.util.ArrayList;
import java.util.Map;

public interface CommandInterface {
  void execute(Map<String, ArrayList<String>> childParentDependencies,
               Map<String, ArrayList<String>> parentChildDependencies);
}
