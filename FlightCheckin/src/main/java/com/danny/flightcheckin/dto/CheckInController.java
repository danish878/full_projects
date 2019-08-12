package com.danny.flightcheckin.dto;

import com.danny.flightcheckin.integration.ReservationRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckInController {

    @Autowired
    private ReservationRestClient restClient;

    @GetMapping("/showFlights")
    public String showFlights() {
        return "showFlights";
    }

    @GetMapping("/showStartCheckin")
    public String showStartCheckin() {
        return "startCheckIn";
    }

    @PostMapping("/startCheckIn")
    public String startCheckIn(@RequestParam("reservationId") Long reservationId, Model model) {
        Reservation reservation = restClient.findReservation(reservationId);
        model.addAttribute("reservation", reservation);
        return "displayReservationDetails";
    }

    @PostMapping("/completeCheckIn")
    public String completeCheckIn(@RequestParam("reservationId") Long reservationId,
                                  @RequestParam("numberOfBags") int numberOfBags) {

        ReservationUpdateRequest reservationUpdateRequest = new ReservationUpdateRequest();
        reservationUpdateRequest.setId(reservationId);
        reservationUpdateRequest.setNumberOfBags(numberOfBags);
        reservationUpdateRequest.setCheckedIn(true);

        restClient.updateReservation(reservationUpdateRequest);

        return "checkInConfirmation";
    }
}
