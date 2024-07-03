package com.buser.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String passengerName;
    @Column(nullable = false, length = 15)
    private String documentNumber;
    @Column(nullable = false)
    private LocalDateTime reservationTime;
    
    @Column(nullable = false)
    private String token;
    
    @Column(nullable = false)
    private double totalPrice;

    @ManyToMany
    @JoinTable(
        name = "reservation_seat",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seats;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    public Reservation() {
    }

    public Reservation(String passengerName,String documentNumber, LocalDateTime reservationTime, String token, List<Seat> seats, Trip trip) {
        this.passengerName = passengerName;
        this.documentNumber = documentNumber;
        this.reservationTime = reservationTime;
        this.token = token;
        this.seats = seats;
        this.trip = trip;
        this.totalPrice = seats.stream().mapToDouble(Seat::getPrice).sum();
    }
   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

}
