package com.danny.flightreservation.controller;

import com.danny.flightreservation.dto.ReservationRequest;
import com.danny.flightreservation.entity.Flight;
import com.danny.flightreservation.entity.Reservation;
import com.danny.flightreservation.repository.FlightRepository;
import com.danny.flightreservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationController {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/showCompletedReservation")
    public String showCompletedReservation(@RequestParam("flightId") Long flightId, Model model) {
        Flight flight = flightRepository.findById(flightId).get();
        model.addAttribute("flight", flight);
        return "completedReservation";
    }

    @PostMapping("/completedReservation")
    public String completedReservation(ReservationRequest request, Model model) {
        Reservation reservation = reservationService.bookFlight(request);
        model.addAttribute("msg", "Reservation created successfully with id " + reservation.getId());
        return "reservationConfirmation";
    }
}
