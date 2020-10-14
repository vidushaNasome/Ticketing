package com.example.ticket.Model;

public class RouteFair {

    String id;
    String End;
    int RouteId;
    String Start;
    double Fair;
    String Route;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }


    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }


    public String getRoute() {
        return Route;
    }

    public void setRoute(String route) {
        Route = route;
    }

    public int getRouteId() {
        return RouteId;
    }

    public void setRouteId(int routeId) {
        RouteId = routeId;
    }

    public double getFair() {
        return Fair;
    }

    public void setFair(double fair) {
        Fair = fair;
    }
}
