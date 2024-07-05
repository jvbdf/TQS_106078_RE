package com.buser.controller;

import com.buser.model.Trip;
import com.buser.service.implementation.TripServiceImplementation;
import com.buser.model.City;
import com.buser.model.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trip")
public class TripController {

    @Autowired
    private TripServiceImplementation tripService;

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        Trip trip = tripService.getTripById(id);
        return trip != null ? ResponseEntity.ok(trip) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Trip> saveTrip(@RequestBody Trip trip) {
        return ResponseEntity.ok(tripService.saveTrip(trip));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Trip>> searchTrips(@RequestParam Long originId, @RequestParam Long destinationId) {
        City origin = new City();
        origin.setId(originId);
        City destination = new City();
        destination.setId(destinationId);
        return ResponseEntity.ok(tripService.searchTrips(origin, destination));
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<String> getCurrentLocation(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getCurrentLocation(id));
    }

    @PatchMapping("/{id}/location")
    public ResponseEntity<Trip> updateCurrentLocation(@PathVariable Long id, @RequestBody String currentLocation) {
        return ResponseEntity.ok(tripService.updateCurrentLocation(id, currentLocation));
    }

    @GetMapping("/arrivals")
    public ResponseEntity<List<Trip>> getNextArrivals(@RequestParam String terminal, @RequestParam int limit) {
        return ResponseEntity.ok(tripService.getNextArrivals(terminal, limit));
    }
}
