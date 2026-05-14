package com.courier.management.controller;

import com.courier.management.entity.CourierOfficer;
import com.courier.management.service.CourierOfficerService;
import com.courier.management.service.OfficeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/officers")
@RequiredArgsConstructor
public class CourierOfficerController {

    private final CourierOfficerService courierOfficerService;
    private final OfficeService officeService;

    // Display all officers
    @GetMapping
    public String listOfficers(Model model) {
        List<CourierOfficer> officers = courierOfficerService.getAllCourierOfficers();
        model.addAttribute("officers", officers);
        return "manager-list";
    }

    // Show form to add new officer
    @GetMapping("/add")
    public String showAddOfficerForm(Model model) {
        model.addAttribute("officer", new CourierOfficer());
        model.addAttribute("offices", officeService.getAllOffices());
        return "add-new-officer";
    }

    // Process add officer form
    @PostMapping("/add")
    public String addOfficer(@Valid @ModelAttribute("officer") CourierOfficer officer,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("offices", officeService.getAllOffices());
            return "add-new-officer";
        }

        try {
            courierOfficerService.createCourierOfficer(officer);
            redirectAttributes.addFlashAttribute("successMessage", "Officer added successfully");
            return "redirect:/officers/add-success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding officer: " + e.getMessage());
            model.addAttribute("offices", officeService.getAllOffices());
            return "redirect:/officers/add";
        }
    }

    // Show add success page
    @GetMapping("/add-success")
    public String showAddSuccess() {
        return "manager-add-success";
    }

    // View officer details
    @GetMapping("/{id}")
    public String viewOfficer(@PathVariable Long id, Model model) {
        CourierOfficer officer = courierOfficerService.getCourierOfficerById(id)
            .orElseThrow(() -> new RuntimeException("Officer not found"));
        model.addAttribute("officer", officer);
        return "officer-details";
    }

    // Edit officer
    @GetMapping("/{id}/edit")
    public String showEditOfficerForm(@PathVariable Long id, Model model) {
        CourierOfficer officer = courierOfficerService.getCourierOfficerById(id)
            .orElseThrow(() -> new RuntimeException("Officer not found"));
        model.addAttribute("officer", officer);
        model.addAttribute("offices", officeService.getAllOffices());
        return "edit-officer";
    }

    // Update officer
    @PostMapping("/{id}/edit")
    public String updateOfficer(@PathVariable Long id,
                              @Valid @ModelAttribute("officer") CourierOfficer officer,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("offices", officeService.getAllOffices());
            return "edit-officer";
        }

        try {
            officer.setId(id);
            courierOfficerService.updateCourierOfficer(officer);
            redirectAttributes.addFlashAttribute("successMessage", "Officer updated successfully");
            return "redirect:/officers/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating officer: " + e.getMessage());
            model.addAttribute("offices", officeService.getAllOffices());
            return "redirect:/officers/" + id + "/edit";
        }
    }

    // Delete officer
    @PostMapping("/{id}/delete")
    public String deleteOfficer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courierOfficerService.deleteCourierOfficer(id);
            redirectAttributes.addFlashAttribute("successMessage", "Officer deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting officer: " + e.getMessage());
        }
        return "redirect:/officers";
    }

    // Get officers by office
    @GetMapping("/office/{office}")
    public String listOfficersByOffice(@PathVariable String office, Model model) {
        List<CourierOfficer> officers = courierOfficerService.getCourierOfficersByOffice(office);
        model.addAttribute("officers", officers);
        model.addAttribute("office", office);
        return "officers-by-office";
    }
}