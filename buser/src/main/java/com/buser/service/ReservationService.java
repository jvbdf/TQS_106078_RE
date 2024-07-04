package com.buser.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buser.model.Reservation;
import com.buser.model.Seat;
import com.buser.model.Trip;
import com.buser.repository.ReservationRepository;
import com.buser.repository.SeatRepository;
import com.buser.repository.TripRepository;

@Service
public class ReservationService {
    
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TripRepository tripRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public Reservation createReservation(String passengerName, String documentNumber, Long tripId, List<Long> seatIds) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));
        List<Seat> seats = seatRepository.findAllById(seatIds);

        for (Seat seat : seats) {
            if (seat.isReserved()) {
                throw new RuntimeException("Seat " + seat.getSeatNumber() + " is already reserved");
            }
        }

        for (Seat seat : seats) {
            seat.setReserved(true);
            seatRepository.save(seat);
        }

        String token = UUID.randomUUID().toString();
        Reservation reservation = new Reservation(passengerName, documentNumber, LocalDateTime.now(), token, seats, trip);
        return reservationRepository.save(reservation);
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        for (Seat seat : reservation.getSeats()) {
            seat.setReserved(false);
            seatRepository.save(seat);
        }

        reservationRepository.delete(reservation);
    }

    public void changeReservation(Long reservationId, List<Long> newSeatIds) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        for (Seat seat : reservation.getSeats()) {
            seat.setReserved(false);
            seatRepository.save(seat);
        }

        List<Seat> newSeats = seatRepository.findAllById(newSeatIds);

        for (Seat newSeat : newSeats) {
            if (newSeat.isReserved()) {
                throw new RuntimeException("Seat " + newSeat.getSeatNumber() + " is already reserved");
            }
        }

        for (Seat newSeat : newSeats) {
            newSeat.setReserved(true);
            seatRepository.save(newSeat);
        }

        reservation.setSeats(newSeats);
        reservation.setTotalPrice(newSeats.stream().mapToDouble(Seat::getPrice).sum());
        reservationRepository.save(reservation);
    }

    public List<Reservation> findReservationsByToken(String token) {
        return reservationRepository.findByToken(token);
    }


}
