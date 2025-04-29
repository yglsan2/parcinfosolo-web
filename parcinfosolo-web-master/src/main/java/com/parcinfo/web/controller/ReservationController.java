package com.parcinfo.web.controller;

import com.parcinfo.web.model.Reservation;
import com.parcinfo.web.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public String listReservations(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        return "reservations/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservations/form";
    }

    @GetMapping("/{id}")
    public String viewReservation(@PathVariable Long id, Model model) {
        Reservation reservation = reservationService.findById(id);
        model.addAttribute("reservation", reservation);
        return "reservations/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Reservation reservation = reservationService.findById(id);
        model.addAttribute("reservation", reservation);
        return "reservations/form";
    }

    @PostMapping
    public String createReservation(@ModelAttribute Reservation reservation, RedirectAttributes redirectAttributes) {
        try {
            reservationService.save(reservation);
            redirectAttributes.addFlashAttribute("message", "Réservation créée avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la création de la réservation");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/reservations";
    }

    @PostMapping("/{id}")
    public String updateReservation(@PathVariable Long id, @ModelAttribute Reservation reservation, RedirectAttributes redirectAttributes) {
        try {
            reservation.setId(id);
            reservationService.save(reservation);
            redirectAttributes.addFlashAttribute("message", "Réservation mise à jour avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la mise à jour de la réservation");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/reservations";
    }

    @GetMapping("/{id}/delete")
    public String deleteReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservationService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Réservation supprimée avec succès");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la suppression de la réservation");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        }
        return "redirect:/reservations";
    }
} 