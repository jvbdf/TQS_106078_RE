package com.buser.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buser.model.City;
import com.buser.model.Route;
import com.buser.repository.RouteRepository;

@Service
public class RouteServiceImplementation {
    @Autowired
    private RouteRepository routeRepository;

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id).orElse(null);
    }

    public Route saveRoute(Route route) {
        return routeRepository.save(route);
    }

    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }

    public List<Route> findRoutesByOriginAndDestination(City origin, City destination) {
        return routeRepository.findByOriginAndDestination(origin, destination);
    }
}
