package com.bank.dao;

import org.springframework.data.repository.CrudRepository;

import com.bank.domain.Appointment;

public interface AppointmentDao extends CrudRepository<Appointment, Long> {

}
