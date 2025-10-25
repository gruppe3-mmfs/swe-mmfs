package org.gruppe3.core.domain;

public class Route {
  private int id;
  private String name;
  private String originName;
  private String destinationName;
  private StopPlace originLoc;
  private StopPlace destinationLoc;

  public Route(int id, String name, String originName, String destinationName) {
    this.id = id;
    this.name = name;
    this.originName = originName;
    this.destinationName = destinationName;
  }

  public StopPlace getOriginLoc() {
    return originLoc;
  }

  public void setOriginLoc(StopPlace originLoc) {
    this.originLoc = originLoc;
  }

  public StopPlace getDestinationLoc() {
    return destinationLoc;
  }

  public void setDestinationLoc(StopPlace destinationLoc) {
    this.destinationLoc = destinationLoc;
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
    return originName;
  }

  public void setFromName(String fromName) {
    this.originName = fromName;
  }

  public String getToName() {
    return destinationName;
  }

  public void setToName(String toName) {
    this.destinationName = toName;
  }
}
