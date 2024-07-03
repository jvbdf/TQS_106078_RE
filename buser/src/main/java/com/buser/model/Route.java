package com.buser.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "route")
public class Route {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origin_id")
    private City origin;

    @ManyToOne
    @JoinColumn(name = "origin_id", nullable = false)
    private City destination;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private int availableSeats;

    @Column(nullable = false)
    private int normalSeats;

    @Column(nullable = false)
    private int premiumSeats;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<Trip> trips;

    public Route() {
    }

    public Route(City origin, City destination, int totalSeats, int availableSeats, int normalSeats, int premiumSeats) {
        this.origin = origin;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.normalSeats = normalSeats;
        this.premiumSeats = premiumSeats;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getOrigin() {
        return origin;
    }

    public void setOrigin(City origin) {
        this.origin = origin;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getNormalSeats() {
        return normalSeats;
    }

    public void setNormalSeats(int normalSeats) {
        this.normalSeats = normalSeats;
    }

    public int getPremiumSeats() {
        return premiumSeats;
    }

    public void setPremiumSeats(int premiumSeats) {
        this.premiumSeats = premiumSeats;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
    

}
