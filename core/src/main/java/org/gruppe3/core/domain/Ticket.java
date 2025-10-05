package org.gruppe3.core.domain;

import java.util.ArrayList;

public class Ticket {
  private int id;
  private Route route;
  private ArrayList<Route> routes;

  public Ticket(int id, Route route) {
    this.id = id;
    this.route = route;
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
