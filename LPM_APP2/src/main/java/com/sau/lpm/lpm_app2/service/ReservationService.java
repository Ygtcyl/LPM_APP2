package com.sau.lpm.lpm_app2.service;

import com.sau.lpm.lpm_app2.dtos.ReservationDTO;
import com.sau.lpm.lpm_app2.model.Reservation;

import java.util.List;

public interface ReservationService {
    public List<Reservation> getAllReservations();
    public Reservation getReservationById(Long id);
    public Reservation createReservation(Reservation reservation);
    public Reservation updateReservation(Long id, Reservation reservation);
    public void deleteReservation(Long id);
}