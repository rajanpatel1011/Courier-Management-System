package com.courier.management.controller;

import com.courier.management.entity.Courier;
import com.courier.management.service.CourierService;
import com.courier.management.service.CourierTrackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/couriers")
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;
    private final CourierTrackService courierTrackService;

    // Display all couriers
    @GetMapping
    public String listCouriers(Model model) {
        List<Courier> couriers = courierService.getAllCouriers();
        model.addAttribute("couriers", couriers);
        return "courier-list";
    }

    // Show form to add new courier
    @GetMapping("/add")
    public String showAddCourierForm(Model model) {
        model.addAttribute("courier", new Courier());
        return "add-courier";
    }

    // Process add courier form
    @PostMapping("/add")
    public String addCourier(@Valid @ModelAttribute("courier") Courier courier,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "add-courier";
        }

        try {
            Courier savedCourier = courierService.createCourier(courier);
            redirectAttributes.addFlashAttribute("successMessage",
                "Courier added successfully with Consignment No: " + savedCourier.getConsignmentNo());
            return "redirect:/couriers/add-success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding courier: " + e.getMessage());
            return "redirect:/couriers/add";
        }
    }

    // Show add success page
    @GetMapping("/add-success")
    public String showAddSuccess() {
        return "courier-add-success";
    }

    // View courier details
    @GetMapping("/{id}")
    public String viewCourier(@PathVariable Long id, Model model) {
        Courier courier = courierService.getCourierById(id)
            .orElseThrow(() -> new RuntimeException("Courier not found"));
        model.addAttribute("courier", courier);
        model.addAttribute("trackingHistory", courierTrackService.getTrackingByCourierId(id));
        return "courier-details";
    }

    // Edit courier
    @GetMapping("/{id}/edit")
    public String showEditCourierForm(@PathVariable Long id, Model model) {
        Courier courier = courierService.getCourierById(id)
            .orElseThrow(() -> new RuntimeException("Courier not found"));
        model.addAttribute("courier", courier);
        return "edit-courier";
    }

    // Update courier
    @PostMapping("/{id}/edit")
    public String updateCourier(@PathVariable Long id,
                              @Valid @ModelAttribute("courier") Courier courier,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit-courier";
        }

        try {
            courier.setId(id);
            courierService.updateCourier(courier);
            redirectAttributes.addFlashAttribute("successMessage", "Courier updated successfully");
            return "redirect:/couriers/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating courier: " + e.getMessage());
            return "redirect:/couriers/" + id + "/edit";
        }
    }

    // Mark courier as delivered
    @PostMapping("/{id}/deliver")
    public String markAsDelivered(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courierService.markAsDelivered(id);
            redirectAttributes.addFlashAttribute("successMessage", "Courier marked as delivered");
            return "redirect:/couriers/delivered-success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error marking courier as delivered: " + e.getMessage());
            return "redirect:/couriers";
        }
    }

    // Show delivered success page
    @GetMapping("/delivered-success")
    public String showDeliveredSuccess() {
        return "delivered-success";
    }

    // Search couriers
    @GetMapping("/search")
    public String searchCouriers(@RequestParam(required = false) String query,
                               @RequestParam(required = false) String type,
                               Model model) {
        List<Courier> results = List.of();

        if (query != null && !query.trim().isEmpty()) {
            if ("consignment".equals(type)) {
                var courierOpt = courierService.getCourierByConsignmentNo(query.trim());
                if (courierOpt.isPresent()) {
                    results = List.of(courierOpt.get());
                }
            } else if ("shipper".equals(type)) {
                results = courierService.searchByShipperName(query.trim());
            } else if ("receiver".equals(type)) {
                results = courierService.searchByReceiverName(query.trim());
            }
        }

        model.addAttribute("couriers", results);
        model.addAttribute("searchQuery", query);
        model.addAttribute("searchType", type);
        return "search-courier";
    }

    // Track courier by consignment number
    @GetMapping("/track")
    public String trackCourier(@RequestParam String consignmentNo, Model model) {
        Courier courier = courierService.getCourierByConsignmentNo(consignmentNo)
            .orElse(null);

        if (courier != null) {
            model.addAttribute("courier", courier);
            model.addAttribute("trackingHistory", courierTrackService.getTrackingByConsignmentNo(consignmentNo));
        } else {
            model.addAttribute("errorMessage", "Courier not found with consignment number: " + consignmentNo);
        }

        return "track-result";
    }

    // Get delivered couriers
    @GetMapping("/delivered")
    public String listDeliveredCouriers(Model model) {
        List<Courier> deliveredCouriers = courierService.getDeliveredCouriers();
        model.addAttribute("couriers", deliveredCouriers);
        return "delivered-list";
    }

    // Get couriers by status
    @GetMapping("/status/{status}")
    public String listCouriersByStatus(@PathVariable String status, Model model) {
        List<Courier> couriers = courierService.getCouriersByStatus(status);
        model.addAttribute("couriers", couriers);
        model.addAttribute("status", status);
        return "courier-status";
    }

    // Delete courier
    @PostMapping("/{id}/delete")
    public String deleteCourier(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courierService.deleteCourier(id);
            redirectAttributes.addFlashAttribute("successMessage", "Courier deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting courier: " + e.getMessage());
        }
        return "redirect:/couriers";
    }
}