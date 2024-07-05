package com.buser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buser.model.City;
import com.buser.repository.CityRepository;


@Service
public class CityServiceImplementation implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    @Override
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public List<City> findCitiesByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllCities();  // If name is null or empty, return all cities
        }
        return cityRepository.findByNameContainingIgnoreCase(name);
    }
}