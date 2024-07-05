package com.buser.service;

import java.util.List;

import com.buser.model.City;
import com.buser.model.Route;

public interface RouteService {
    List<Route> getAllRoutes();
    Route getRouteById(Long id);
    Route saveRoute(Route route);
    void deleteRoute(Long id);
    List<Route> findRoutesByOriginAndDestination(City origin, City destination);
}