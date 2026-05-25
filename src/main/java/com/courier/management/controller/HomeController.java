package com.courier.management.controller;

import com.courier.management.service.CourierService;
import com.courier.management.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CourierService courierService;
    private final OfficeService officeService;

    // Home page
    @GetMapping("/")
    public String home(Model model) {
        // Add some statistics for the dashboard
        long totalCouriers = courierService.countAllCouriers();
        long deliveredCouriers = courierService.countCouriersByStatus("Delivered");
        long inTransitCouriers = courierService.countCouriersByStatus("In Transit");
        long totalOffices = officeService.getAllOffices().size();

        model.addAttribute("totalCouriers", totalCouriers);
        model.addAttribute("deliveredCouriers", deliveredCouriers);
        model.addAttribute("inTransitCouriers", inTransitCouriers);
        model.addAttribute("totalOffices", totalOffices);

        return "index";
    }

    // About page or other static pages can be added here
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // Contact page
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    // Success page
    @GetMapping("/success")
    public String success(@RequestParam(required = false) String cid, Model model) {
        model.addAttribute("cid", cid);
        return "success";
    }

    // Search by consignment number to edit shipment (admin)
    @GetMapping("/search-edit")
    public String searchEdit() {
        return "search-edit";
    }

    @PostMapping("/search-edit")
    public String searchEditSubmit(@RequestParam String consignmentNo,
                                   RedirectAttributes redirectAttributes) {
        return courierService.getCourierByConsignmentNo(consignmentNo.trim())
            .map(courier -> "redirect:/couriers/" + courier.getId() + "/edit")
            .orElseGet(() -> {
                redirectAttributes.addFlashAttribute("errorMessage",
                    "Consignment number not found. Please try again.");
                return "redirect:/search-edit";
            });
    }

    // Track status page
    @GetMapping("/track-status")
    public String trackStatus() {
        return "track-status";
    }
}