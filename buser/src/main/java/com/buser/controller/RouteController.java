package com.buser.controller;

import com.buser.model.Route;
import com.buser.service.implementation.RouteServiceImplementation;
import com.buser.model.City;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteServiceImplementation routeService;

    @GetMapping
    public ResponseEntity<List<Route>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable Long id) {
        Route route = routeService.getRouteById(id);
        return route != null ? ResponseEntity.ok(route) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Route> saveRoute(@RequestBody Route route) {
        return ResponseEntity.ok(routeService.saveRoute(route));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Route>> findRoutesByOriginAndDestination(@RequestParam Long originId, @RequestParam Long destinationId) {
        City origin = new City();
        origin.setId(originId);
        City destination = new City();
        destination.setId(destinationId);
        return ResponseEntity.ok(routeService.findRoutesByOriginAndDestination(origin, destination));
    }
}