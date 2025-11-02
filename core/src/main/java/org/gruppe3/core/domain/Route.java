package org.gruppe3.core.domain;

public class Route {
  private String name;
  private String originName;
  private String destinationName;
  private Location originLoc;
  private Location destinationLoc;

  public Route(String originName, String destinationName) {
    this.name = originName + " - " + destinationName;
    this.originName = originName;
    this.destinationName = destinationName;
  }

  public Route(Location originLoc, Location destinationLoc) {
    this.originLoc = originLoc;
    this.destinationLoc = destinationLoc;
    this.originName = originLoc.getName();
    this.destinationName = destinationLoc.getName();
  }

  public Location getOriginLoc() {
    return originLoc;
  }

  public void setOriginLoc(Location originLoc) {
    this.originLoc = originLoc;
  }

  public Location getDestinationLoc() {
    return destinationLoc;
  }

  public void setDestinationLoc(Location destinationLoc) {
    this.destinationLoc = destinationLoc;
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
