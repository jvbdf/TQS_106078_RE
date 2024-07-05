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
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TripRepository tripRepository;

    @Override
    public Reservation createReservation(String passengerName, String documentNumber, String email, String phone, Long tripId, List<Long> seatIds) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));
        List<Seat> seats = seatRepository.findAllById(seatIds);

        if (seats.size() > trip.getAvailableSeats()) {
            throw new RuntimeException("Not enough available seats");
        }

        for (Seat seat : seats) {
            if (seat.isReserved()) {
                throw new RuntimeException("Seat " + seat.getSeatNumber() + " is already reserved");
            }
        }

        trip.reserveSeats(seats.size());

        for (Seat seat : seats) {
            seat.setReserved(true);
            seatRepository.save(seat);
        }

        UUID token = UUID.randomUUID();
        Reservation reservation = new Reservation(passengerName, documentNumber, email, phone, LocalDateTime.now(), token, seats, trip);
        reservation.setTotalPrice(seats.stream().mapToDouble(Seat::getPrice).sum());

        tripRepository.save(trip);

        return reservationRepository.save(reservation);
    }

    @Override
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        for (Seat seat : reservation.getSeats()) {
            seat.setReserved(false);
            seatRepository.save(seat);
        }

        Trip trip = reservation.getTrip();
        trip.releaseSeats(reservation.getSeats().size());
        tripRepository.save(trip);

        reservationRepository.delete(reservation);
    }

    @Override
    public void changeReservation(Long reservationId, List<Long> newSeatIds) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Trip trip = reservation.getTrip();

        for (Seat seat : reservation.getSeats()) {
            seat.setReserved(false);
            seatRepository.save(seat);
        }

        trip.releaseSeats(reservation.getSeats().size());

        List<Seat> newSeats = seatRepository.findAllById(newSeatIds);

        if (newSeats.size() > trip.getAvailableSeats()) {
            throw new RuntimeException("Not enough available seats");
        }

        for (Seat newSeat : newSeats) {
            if (newSeat.isReserved()) {
                throw new RuntimeException("Seat " + newSeat.getSeatNumber() + " is already reserved");
            }
        }

        trip.reserveSeats(newSeats.size());

        for (Seat newSeat : newSeats) {
            newSeat.setReserved(true);
            seatRepository.save(newSeat);
        }

        reservation.setSeats(newSeats);
        reservation.setTotalPrice(newSeats.stream().mapToDouble(Seat::getPrice).sum());
        reservationRepository.save(reservation);

        tripRepository.save(trip);
    }

    @Override
    public Reservation getReservationByToken(UUID token) {
        return reservationRepository.findByToken(token);
    }

    @Override
    public List<Reservation> getReservationsByTripId(Long tripId) {
        return reservationRepository.findByTripId(tripId);
    }
}
