package org.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Node {
  private String nodeId;
  private String nodeName;
  private final Map<String, String> additionalInfo;
  private final ArrayList<String> nodeParents;
  private final ArrayList<String> nodeChildren;

  public Node() {
    this.additionalInfo = new HashMap<>();
    this.nodeChildren = new ArrayList<>();
    this.nodeParents = new ArrayList<>();
  }

  public void setNodeId(final String nodeId) {
    this.nodeId = nodeId;
  }

  public String getNodeId() {
    return nodeId;
  }

  public void setNodeName(final String nodeName) {
    this.nodeName = nodeName;
  }

  public String getNodeName() {
    return nodeName;
  }

  public void addInfo(final String key, final String value) {
    additionalInfo.put(key, value);
  }

  public String getInfo(final String key) {
    return additionalInfo.get(key);
  }

  public Map<String, String> getAllInfo() {
    return additionalInfo;
  }

  public ArrayList<String> getNodeParents() {
    return nodeParents;
  }

  public void setNodeParents(final ArrayList<String> nodeParents) {
    this.nodeParents.clear();
    this.nodeParents.addAll(nodeParents);
  }

  public ArrayList<String> getNodeChildren() {
    return nodeChildren;
  }

  public void setNodeChildren(final ArrayList<String> nodeChildren) {
    this.nodeChildren.clear();
    this.nodeChildren.addAll(nodeChildren);
  }
}
