package com.bank.service;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

import com.bank.domain.Appointment;

public interface AppointmentService {

    void createAppointment(Appointment appointment, String date, Principal principal) throws ParseException;

    List<Appointment> findAll();

    Appointment findAppointmentById(Long id);

    void confirmAppointment(Long id);
}
