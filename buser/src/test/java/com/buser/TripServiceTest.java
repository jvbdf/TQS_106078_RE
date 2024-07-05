package com.buser;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.buser.model.City;
import com.buser.model.Route;
import com.buser.model.Trip;
import com.buser.repository.CityRepository;
import com.buser.repository.TripRepository;
import com.buser.service.TripServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private TripServiceImpl tripService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTripsBetweenCities() {
        City origin = new City("CityA");
        City destination = new City("CityB");
        Route route = new Route(origin, destination, LocalDateTime.now(), LocalDateTime.now().plusHours(5));
        Trip trip = new Trip(route);

        when(tripRepository.findByRouteOriginNameAndRouteDestinationName("CityA", "CityB"))
            .thenReturn(Arrays.asList(trip));

        List<Trip> trips = tripService.getTripsBetweenCities("CityA", "CityB");
        assertNotNull(trips);
        assertEquals(1, trips.size());
    }

    @Test
    public void testGetTripById() {
        Trip trip = new Trip(new Route(new City("CityA"), new City("CityB"), LocalDateTime.now(), LocalDateTime.now().plusHours(5)));

        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));

        Trip foundTrip = tripService.getTripById(1L);
        assertNotNull(foundTrip);
        assertEquals(trip, foundTrip);
    }

    @Test
    public void testUpdateCurrentLocation() {
        Trip trip = new Trip(new Route(new City("CityA"), new City("CityB"), LocalDateTime.now(), LocalDateTime.now().plusHours(5)));
        trip.setId(1L);

        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));

        Trip updatedTrip = tripService.updateCurrentLocation(1L, "New Location");
        assertNotNull(updatedTrip);
        assertEquals("New Location", updatedTrip.getCurrentLocation());
    }

    @Test
    public void testGetUpcomingTripsByOrigin() {
        City origin = new City("CityA");
        Route route = new Route(origin, new City("CityB"), LocalDateTime.now(), LocalDateTime.now().plusHours(5));
        Trip trip = new Trip(route);

        when(tripRepository.findByRouteOriginName("CityA")).thenReturn(Arrays.asList(trip));

        List<Trip> trips = tripService.getUpcomingTripsByOrigin("CityA");
        assertNotNull(trips);
        assertEquals(1, trips.size());
    }

    @Test
    public void testGetCityById() {
        City city = new City("CityA");

        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        City foundCity = tripService.getCityById(1L);
        assertNotNull(foundCity);
        assertEquals(city, foundCity);
    }
}
