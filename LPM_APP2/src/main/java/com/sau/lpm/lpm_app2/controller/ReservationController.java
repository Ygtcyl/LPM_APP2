package com.sau.lpm.lpm_app2.controller;

import com.sau.lpm.lpm_app2.dtos.ReservationDTO;
import com.sau.lpm.lpm_app2.model.Reservation;
import com.sau.lpm.lpm_app2.service.StudentService;
import com.sau.lpm.lpm_app2.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;
    private final StudentService studentService;

    public ReservationController(ReservationService reservationService, StudentService studentService) {
        this.reservationService = reservationService;
        this.studentService = studentService;
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<Reservation>> getReservations() {
        return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(reservationService.getReservationById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        LOGGER.info("Adding reservation {}", reservation);
        return new ResponseEntity<>(reservationService.createReservation(reservation), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        if (id == null || id == 0 || reservation == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(reservationService.updateReservation(id, reservation), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}