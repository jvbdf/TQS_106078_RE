package com.buser.controller;

import com.buser.model.Reservation;
import com.buser.service.implementation.ReservationServiceImplementationImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationServiceImplementationImplementation reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        return reservation != null ? ResponseEntity.ok(reservation) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationRequest request) {
        return ResponseEntity.ok(reservationService.createReservation(
            request.getPassengerName(), request.getDocumentNumber(), request.getTripId(), request.getSeatIds()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Reservation> changeReservation(@PathVariable Long id, @RequestBody List<Long> newSeatIds) {
        reservationService.changeReservation(id, newSeatIds);
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<List<Reservation>> findReservationsByToken(@PathVariable String token) {
        return ResponseEntity.ok(reservationService.findReservationsByToken(token));
    }
}

class CreateReservationRequest {
    private String passengerName;
    private String documentNumber;
    private Long tripId;
    private List<Long> seatIds;

    // Getters and Setters
}
