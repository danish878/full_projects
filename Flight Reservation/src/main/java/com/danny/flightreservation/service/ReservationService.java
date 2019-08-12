package com.danny.flightreservation.service;

import com.danny.flightreservation.dto.ReservationRequest;
import com.danny.flightreservation.entity.Reservation;

public interface ReservationService {

    Reservation bookFlight(ReservationRequest request);
}
