package com.buser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buser.model.City;
import com.buser.service.CityServiceImplementation;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityServiceImplementation cityService;

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        City city = cityService.getCityById(id);
        return city != null ? ResponseEntity.ok(city) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<City> saveCity(@RequestBody City city) {
        return ResponseEntity.ok(cityService.saveCity(city));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<City>> findCitiesByName(@RequestParam String name) {
        return ResponseEntity.ok(cityService.findCitiesByName(name));
    }
}