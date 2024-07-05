package com.buser.controller;

import com.buser.model.Seat;
import com.buser.model.Trip;
import com.buser.service.implementation.SeatServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatServiceImplementation seatService;

    @GetMapping
    public ResponseEntity<List<Seat>> getAllSeats() {
        return ResponseEntity.ok(seatService.getAllSeats());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long id) {
        Seat seat = seatService.getSeatById(id);
        return seat != null ? ResponseEntity.ok(seat) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Seat> saveSeat(@RequestBody Seat seat) {
        return ResponseEntity.ok(seatService.saveSeat(seat));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/trip/{tripId}/reserved/{reserved}")
    public ResponseEntity<List<Seat>> findSeatsByTripAndReserved(@PathVariable Long tripId, @PathVariable boolean reserved) {
        Trip trip = new Trip();
        trip.setId(tripId);
        return ResponseEntity.ok(seatService.findSeatsByTripAndReserved(trip, reserved));
    }
}
