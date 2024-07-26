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

  public void setNodeId(String nodeId) {
    this.nodeId = nodeId;
  }

  public String getNodeId() {
    return nodeId;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
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

  public ArrayList<String> getNodeParents() {
    return nodeParents;
  }

  public void setNodeParents(final ArrayList<String> nodeParents) {
    this.nodeParents = nodeParents;
  }

  public ArrayList<String> getNodeChildren() {
    return nodeChildren;
  }

  public void setNodeChildren(final ArrayList<String> nodeChildren) {
    this.nodeChildren = nodeChildren;
  }


  public Node() {
    this.additionalInfo = new HashMap<>();
    this.nodeChildren = new ArrayList<>();
    this.nodeParents = new ArrayList<>();
  }
}
