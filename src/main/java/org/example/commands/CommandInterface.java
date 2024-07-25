package org.example.commands;
import java.util.Map;
import org.example.model.Node;

public interface CommandInterface {
  void execute(Map<String, Node>nodeDependencies);
}
