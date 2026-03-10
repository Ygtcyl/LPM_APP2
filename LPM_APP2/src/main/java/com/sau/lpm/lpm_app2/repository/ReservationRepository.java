package com.sau.lpm.lpm_app2.repository;


import com.sau.lpm.lpm_app2.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
