package com.courier.management.controller;

import com.courier.management.entity.Courier;
import com.courier.management.entity.CourierTrack;
import com.courier.management.service.CourierService;
import com.courier.management.service.CourierTrackService;
import com.courier.management.service.OfficeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tracking")
@RequiredArgsConstructor
public class CourierTrackController {

    private final CourierTrackService courierTrackService;
    private final CourierService courierService;
    private final OfficeService officeService;

    // Show form to update courier status
    @GetMapping("/update/{courierId}")
    public String showUpdateStatusForm(@PathVariable Long courierId, Model model) {
        Courier courier = courierService.getCourierById(courierId)
            .orElseThrow(() -> new RuntimeException("Courier not found"));

        CourierTrack track = new CourierTrack();
        track.setCourierId(courierId);
        track.setConsignmentNo(courier.getConsignmentNo());

        model.addAttribute("courier", courier);
        model.addAttribute("track", track);
        model.addAttribute("offices", officeService.getAllOffices());
        return "courier-status";
    }

    // Process status update
    @PostMapping("/update")
    public String updateCourierStatus(@Valid @ModelAttribute("track") CourierTrack track,
                                    BindingResult result,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            Courier courier = courierService.getCourierById(track.getCourierId())
                .orElseThrow(() -> new RuntimeException("Courier not found"));
            model.addAttribute("courier", courier);
            model.addAttribute("offices", officeService.getAllOffices());
            return "courier-status";
        }

        try {
            // Create tracking record
            courierTrackService.createTrackingRecord(track);

            // Update courier status
            courierService.updateCourierStatus(track.getCourierId(), track.getStatus());

            redirectAttributes.addFlashAttribute("successMessage", "Status updated successfully");
            return "redirect:/tracking/update-success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating status: " + e.getMessage());
            return "redirect:/tracking/update/" + track.getCourierId();
        }
    }

    // Show update success page
    @GetMapping("/update-success")
    public String showUpdateSuccess() {
        return "update-success";
    }

    // View tracking history for a courier
    @GetMapping("/history/{courierId}")
    public String viewTrackingHistory(@PathVariable Long courierId, Model model) {
        Courier courier = courierService.getCourierById(courierId)
            .orElseThrow(() -> new RuntimeException("Courier not found"));

        model.addAttribute("courier", courier);
        model.addAttribute("trackingHistory", courierTrackService.getTrackingByCourierId(courierId));
        return "courier-tracking-history";
    }

    // View tracking history by consignment number
    @GetMapping("/history/consignment/{consignmentNo}")
    public String viewTrackingHistoryByConsignment(@PathVariable String consignmentNo, Model model) {
        Courier courier = courierService.getCourierByConsignmentNo(consignmentNo)
            .orElse(null);

        if (courier != null) {
            model.addAttribute("courier", courier);
            model.addAttribute("trackingHistory", courierTrackService.getTrackingByConsignmentNo(consignmentNo));
        } else {
            model.addAttribute("errorMessage", "Courier not found with consignment number: " + consignmentNo);
        }

        return "courier-tracking-history";
    }
}