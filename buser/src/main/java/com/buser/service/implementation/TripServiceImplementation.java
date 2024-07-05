package com.buser.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buser.model.City;
import com.buser.model.Route;
import com.buser.model.Trip;
import com.buser.repository.RouteRepository;
import com.buser.repository.TripRepository;


@Service
public class TripServiceImplementation {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private RouteRepository routeRepository;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    public Trip saveTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    public List<Trip> findTripsByRoute(Route route) {
        return tripRepository.findByRoute(route);
    }

    public Trip updateCurrentLocation(Long id, String currentLocation) { //Ainda falta implementar a lógica para funcionar 
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Trip not found"));
        trip.setCurrentLocation(currentLocation);
        return tripRepository.save(trip);
    }

    public String getCurrentLocation(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));
        return trip.getCurrentLocation();
    }

    public List<Trip> getNextArrivals(String terminal, int limit) {
        return tripRepository.findAll().stream()
                .filter(trip -> trip.getRoute().getDestination().getName().equalsIgnoreCase(terminal))
                .sorted((t1, t2) -> t1.getArrivalTime().compareTo(t2.getArrivalTime()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Trip> searchTrips(City origin, City destination) {
        List<Route> routes = routeRepository.findByOriginAndDestination(origin, destination);
        return routes.stream()
                .flatMap(route -> tripRepository.findByRoute(route).stream())
                .collect(Collectors.toList());
    }
}
