package com.buser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buser.model.Trip;
import com.buser.repository.TripRepository;

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    private TripRepository tripRepository;

    @Override
    public List<Trip> getTripsBetweenCities(String originName, String destinationName) {
        return tripRepository.findByRouteOriginNameAndRouteDestinationName(originName, destinationName);
    }

    @Override
    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    @Override
    public Trip updateCurrentLocation(Long tripId, String currentLocation) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));
        trip.setCurrentLocation(currentLocation);
        return tripRepository.save(trip);
    }

    @Override
    public List<Trip> getUpcomingTripsByOrigin(String originName) {
        return tripRepository.findByRouteOriginName(originName);
    }
}
