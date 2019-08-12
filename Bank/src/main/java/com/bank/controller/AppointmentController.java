package com.bank.controller;

import java.security.Principal;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bank.domain.Appointment;
import com.bank.service.AppointmentService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/create")
    public String createAppointment(Model model) {

        model.addAttribute("appointment", new Appointment());
        model.addAttribute("dateString", "");

        return "appointment";
    }

    @PostMapping("/create")
    public String createAppointment(
            @ModelAttribute("appointment") Appointment appointment,
            @ModelAttribute("dateString") String date,
            Principal principal)
            throws ParseException {

        appointmentService.createAppointment(appointment, date, principal);

        return "redirect:/userFront";
    }

}
