package com.bank.service.impl;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.dao.AppointmentDao;
import com.bank.domain.Appointment;
import com.bank.domain.User;
import com.bank.service.AppointmentService;
import com.bank.service.UserService;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentDao appointmentDao;

    @Override
    public void createAppointment(Appointment appointment, String date, Principal principal) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date d1 = format.parse(date);
        appointment.setDate(d1);

        User user = userService.findByUsername(principal.getName());
        appointment.setUser(user);

        appointmentDao.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return (List<Appointment>) appointmentDao.findAll();
    }

    @Override
    public Appointment findAppointmentById(Long id) {
        return appointmentDao.findById(id).get();
    }

    @Override
    public void confirmAppointment(Long id) {
        Appointment appointment = appointmentDao.findById(id).get();
        appointment.setConfirmed(true);

        appointmentDao.save(appointment);

    }


}
