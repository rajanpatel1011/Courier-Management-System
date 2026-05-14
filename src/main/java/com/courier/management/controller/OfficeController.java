package com.courier.management.controller;

import com.courier.management.entity.Office;
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
@RequestMapping("/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    // Display all offices
    @GetMapping
    public String listOffices(Model model) {
        List<Office> offices = officeService.getAllOffices();
        model.addAttribute("offices", offices);
        return "offices-list";
    }

    // Show form to add new office
    @GetMapping("/add")
    public String showAddOfficeForm(Model model) {
        model.addAttribute("office", new Office());
        return "add-office";
    }

    // Process add office form
    @PostMapping("/add")
    public String addOffice(@Valid @ModelAttribute("office") Office office,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "add-office";
        }

        try {
            officeService.createOffice(office);
            redirectAttributes.addFlashAttribute("successMessage", "Office added successfully");
            return "redirect:/offices/add-success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding office: " + e.getMessage());
            return "redirect:/offices/add";
        }
    }

    // Show add success page
    @GetMapping("/add-success")
    public String showAddSuccess() {
        return "office-add-success";
    }

    // View office details
    @GetMapping("/{id}")
    public String viewOffice(@PathVariable Long id, Model model) {
        Office office = officeService.getOfficeById(id)
            .orElseThrow(() -> new RuntimeException("Office not found"));
        model.addAttribute("office", office);
        return "office-details";
    }

    // Edit office
    @GetMapping("/{id}/edit")
    public String showEditOfficeForm(@PathVariable Long id, Model model) {
        Office office = officeService.getOfficeById(id)
            .orElseThrow(() -> new RuntimeException("Office not found"));
        model.addAttribute("office", office);
        return "edit-office";
    }

    // Update office
    @PostMapping("/{id}/edit")
    public String updateOffice(@PathVariable Long id,
                             @Valid @ModelAttribute("office") Office office,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit-office";
        }

        try {
            office.setId(id);
            officeService.updateOffice(office);
            redirectAttributes.addFlashAttribute("successMessage", "Office updated successfully");
            return "redirect:/offices/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating office: " + e.getMessage());
            return "redirect:/offices/" + id + "/edit";
        }
    }

    // Delete office
    @PostMapping("/{id}/delete")
    public String deleteOffice(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            officeService.deleteOffice(id);
            redirectAttributes.addFlashAttribute("successMessage", "Office deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting office: " + e.getMessage());
        }
        return "redirect:/offices";
    }

    // Get offices by city
    @GetMapping("/city/{city}")
    public String listOfficesByCity(@PathVariable String city, Model model) {
        List<Office> offices = officeService.getOfficesByCity(city);
        model.addAttribute("offices", offices);
        model.addAttribute("city", city);
        return "offices-by-city";
    }
}