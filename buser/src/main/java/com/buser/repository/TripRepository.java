package com.buser.repository;

import com.buser.model.Route;
import com.buser.model.Trip;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    public List<Trip> findByRoute(Route route);
}
