package com.buser.service;

import java.util.List;

import com.buser.model.Trip;

public interface TripService {
    List<Trip> getTripsBetweenCities(String originName, String destinationName);
    Trip getTripById(Long id);
    Trip updateCurrentLocation(Long tripId, String currentLocation);
    List<Trip> getUpcomingTripsByOrigin(String originName);
    
}
