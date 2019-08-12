package com.danny.flightreservation.controller;

import com.danny.flightreservation.dto.ReservationUpdateRequest;
import com.danny.flightreservation.entity.Reservation;
import com.danny.flightreservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RestController
@RequestMapping("/reservations")
//@CrossOrigin(origins = "http://localhost:9090")
public class ReservationRESTController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Bean
    public WebMvcConfigurer configure() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/reservations").allowedOrigins("http://localhost:9090");
            }
        };
    }

    @GetMapping
    public List<Reservation> showCompletedReservation() {
        return reservationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Reservation showCompletedReservation(@PathVariable("id") Long id) {
        return reservationRepository.findById(id).get();
    }

    @PostMapping
    public Reservation updateReservation(@RequestBody ReservationUpdateRequest request) {
        Reservation reservation = reservationRepository.findById(request.getId()).get();
        reservation.setCheckedIn(request.getCheckedIn());
        reservation.setNumberOfBags(request.getNumberOfBags());

        return reservationRepository.save(reservation);
    }

}
