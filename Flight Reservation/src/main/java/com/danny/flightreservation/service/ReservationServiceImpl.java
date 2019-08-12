package com.danny.flightreservation.service;

import com.danny.flightreservation.dto.PDFGenerator;
import com.danny.flightreservation.dto.ReservationRequest;
import com.danny.flightreservation.entity.Flight;
import com.danny.flightreservation.entity.Passenger;
import com.danny.flightreservation.entity.Reservation;
import com.danny.flightreservation.repository.FlightRepository;
import com.danny.flightreservation.repository.PassengerRepository;
import com.danny.flightreservation.repository.ReservationRepository;
import com.danny.flightreservation.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PDFGenerator pdfGenerator;

    @Autowired
    private EmailUtil emailUtil;

    @Override
    @Transactional
    public Reservation bookFlight(ReservationRequest request) {

        // Make payment and throw Exception if payment fails and proceed to below if payment is successful

        Flight flight = flightRepository.findById(request.getFlightId()).get();

        Passenger passenger = new Passenger();
        passenger.setFirstName(request.getPassengerFirstName());
        passenger.setLastName(request.getPassengerLastName());
        passenger.setEmail(request.getPassengerEmail());
        passenger.setPhone(request.getPassengerPhone());

        passenger = passengerRepository.save(passenger);

        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        reservation.setCheckedIn(false);

        Reservation savedReservation = reservationRepository.save(reservation);

        String filePath = "C:/Users/Danish/Desktop/reservation/" + reservation.getId() + ".pdf";

        pdfGenerator.generateItinerary(savedReservation, filePath);

        emailUtil.sendItinerary(passenger.getEmail(), filePath);

        return savedReservation;
    }
}
