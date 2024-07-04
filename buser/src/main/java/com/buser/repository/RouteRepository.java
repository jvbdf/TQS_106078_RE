package com.buser.repository;

import com.buser.model.City;
import com.buser.model.Route;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    public List<Route> findByOriginAndDestination(City origin, City destination);
}
