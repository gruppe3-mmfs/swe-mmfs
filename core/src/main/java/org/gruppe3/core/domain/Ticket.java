package org.gruppe3.core.domain;

import java.util.ArrayList;

public class Ticket {
  private int id;
  private Route route;
  private ArrayList<Route> routes;
  private int ownerId;

  public Ticket(int id, Route route) {
    this.id = id;
    this.route = route;
  }

  public int getOwner() {
    return ownerId;
  }

  public void setOwner(int owner) {
    this.ownerId = owner;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Route getRoute() {
    return route;
  }

  public void setRoute(Route route) {
    this.route = route;
  }

  public ArrayList<Route> getRoutes() {
    return routes;
  }

  public void setRoutes(ArrayList<Route> routes) {
    this.routes = routes;
  }
}
