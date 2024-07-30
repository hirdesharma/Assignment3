package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class Node {
  private String nodeId;
  private String nodeName;
  private Map<String, String> additionalInfo;

  public Node(String nodeId, String nodeName) {
    this.nodeId = nodeId;
    this.nodeName = nodeName;
    this.additionalInfo = new HashMap<>();
  }

  public String getNodeId() {
    return nodeId;
  }

  public String getNodeName() {
    return nodeName;
  }

  public void addInfo(String key, String value) {
    additionalInfo.put(key, value);
  }

  public String getInfo(String key) {
    return additionalInfo.get(key);
  }

  public Map<String, String> getAllInfo() {
    return additionalInfo;
  }
}
