package org.example.utility;

import java.util.Map;
import java.util.Objects;
import org.example.model.Node;

public class ValidationUtils {
  public static boolean validateNodeId(final String nodeId,
                                       final Map<String, Node> nodeDependencies) {
    if (Objects.isNull(nodeId) || nodeId.isEmpty() || !nodeDependencies.containsKey(nodeId)) {
      System.out.println("There is no node with id: " + nodeId);
      return true;
    }
    return false;
  }

  public static boolean validateInput(final String nodeId,
                                      final Map<String, Node> nodeDependencies) {
    if (Objects.isNull(nodeId) || nodeId.isEmpty() || nodeDependencies.containsKey(nodeId)) {
      System.out.println("Node with id " + nodeId + " already Exist or nodeId is empty");
      return true;
    }
    return false;
  }

  public static boolean validateParentAndChild(final String childId, final String parentId,
                                               final Map<String, Node> nodeDependencies) {
    if (!nodeDependencies.containsKey(childId) || !nodeDependencies.containsKey(parentId)) {
      System.out.println(
          "There is no node with parentId " + parentId + " or " + "childId " + childId);
      return true;
    }
    return false;
  }
}
