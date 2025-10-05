package org.gruppe3.core.domain;

public class Route {
  private int id;
  private String name;
  private String fromName;
  private String toName;

  public Route(int id, String name, String fromName, String toName) {
    this.id = id;
    this.name = name;
    this.fromName = fromName;
    this.toName = toName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFromName() {
    return fromName;
  }

  public void setFromName(String fromName) {
    this.fromName = fromName;
  }

  public String getToName() {
    return toName;
  }

  public void setToName(String toName) {
    this.toName = toName;
  }
}
