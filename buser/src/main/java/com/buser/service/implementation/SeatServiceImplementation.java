package com.buser.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buser.model.Seat;
import com.buser.model.Trip;
import com.buser.repository.SeatRepository;

@Service
public class SeatServiceImplementation {
    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Seat getSeatById(Long id) {
        return seatRepository.findById(id).orElse(null);
    }

    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public void deleteSeat(Long id) {
        seatRepository.deleteById(id);
    }

    public List<Seat> findSeatsByTripAndReserved(Trip trip, boolean reserved) {
        return seatRepository.findByTripAndReserved(trip, reserved);
    }
}
