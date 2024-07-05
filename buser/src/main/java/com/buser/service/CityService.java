package com.buser.service;

import com.buser.model.City;

import java.util.List;

public interface CityService {
    List<City> getAllCities();
    City getCityById(Long id);
    City saveCity(City city);
    void deleteCity(Long id);
    List<City> findCitiesByName(String name);
}
