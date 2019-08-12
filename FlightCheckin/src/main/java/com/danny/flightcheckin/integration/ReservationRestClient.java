package com.danny.flightcheckin.integration;

import com.danny.flightcheckin.dto.Reservation;
import com.danny.flightcheckin.dto.ReservationUpdateRequest;

public interface ReservationRestClient {

    Reservation findReservation(Long id);

    Reservation updateReservation(ReservationUpdateRequest request);
}
