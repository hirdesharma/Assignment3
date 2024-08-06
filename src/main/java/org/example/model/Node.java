package org.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Node {
  private String nodeId;
  private String nodeName;
  private Map<String, String> additionalInfo;

  private ArrayList<String> nodeParents;

  private ArrayList<String> nodeChildren;

  public final void setNodeId(final String nodeId) {
    this.nodeId = nodeId;
  }

  public final String getNodeId() {
    return nodeId;
  }

  public final void setNodeName(final String nodeName) {
    this.nodeName = nodeName;
  }

  public final String getNodeName() {
    return nodeName;
  }

  public final void addInfo(final String key, final String value) {
    additionalInfo.put(key, value);
  }

  public final String getInfo(final String key) {
    return additionalInfo.get(key);
  }

  public final Map<String, String> getAllInfo() {
    return additionalInfo;
  }

  public final ArrayList<String> getNodeParents() {
    return nodeParents;
  }

  public final void setNodeParents(final ArrayList<String> nodeParents) {
    this.nodeParents = nodeParents;
  }

  public final ArrayList<String> getNodeChildren() {
    return nodeChildren;
  }

  public final void setNodeChildren(final ArrayList<String> nodeChildren) {
    this.nodeChildren = nodeChildren;
  }


  public Node() {
    this.additionalInfo = new HashMap<>();
    this.nodeChildren = new ArrayList<>();
    this.nodeParents = new ArrayList<>();
  }
}
