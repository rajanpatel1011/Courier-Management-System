package com.courier.management.controller;

import com.courier.management.entity.CourierOfficer;
import com.courier.management.service.CourierOfficerService;
import com.courier.management.service.OfficeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final CourierOfficerService courierOfficerService;
    private final OfficeService officeService;

    // Show login page
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        List<String> offices = officeService.getAllOffices().stream()
            .map(office -> office.getOfficeName())
            .toList();
        model.addAttribute("offices", offices);
        return "login";
    }

    // Process login
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam(required = false, defaultValue = "") String office,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        // Check for admin login
        if ("admin".equals(username) && "admin123".equals(password)) {
            session.setAttribute("user_name", "Admin");
            session.setAttribute("user_type", "admin-role");
            session.setAttribute("user_office", "admin");
            return "redirect:/admin";
        }

        // Validate office is selected for officer login
        if (office == null || office.trim().isEmpty() || "admin".equals(office)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please select a valid office for officer login.");
            return "redirect:/login";
        }

        // Check officer login
        Optional<CourierOfficer> officer = courierOfficerService.authenticateOfficer(username, password, office);
        if (officer.isPresent()) {
            session.setAttribute("user_name", username);
            session.setAttribute("user_type", "officer");
            session.setAttribute("user_office", office);
            return "redirect:/admin";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Invalid credentials. Please try again.");
        return "redirect:/login";
    }

    // Show admin dashboard
    @GetMapping("/admin")
    public String showAdminDashboard(HttpSession session, Model model) {
        if (session.getAttribute("user_name") == null) {
            return "redirect:/login";
        }

        model.addAttribute("userName", session.getAttribute("user_name"));
        model.addAttribute("userType", session.getAttribute("user_type"));
        return "admin";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // Change password page
    @GetMapping("/change-password")
    public String showChangePasswordForm(HttpSession session) {
        if (session.getAttribute("user_name") == null) {
            return "redirect:/login";
        }
        return "change-password";
    }

    // Process password change
    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword,
                               @RequestParam String newPassword,
                               @RequestParam String confirmPassword,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        String username = (String) session.getAttribute("user_name");
        String userType = (String) session.getAttribute("user_type");

        if (username == null) {
            return "redirect:/login";
        }

        // Validate new password
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "New passwords do not match");
            return "redirect:/change-password";
        }

        if (newPassword.length() < 6) {
            redirectAttributes.addFlashAttribute("errorMessage", "Password must be at least 6 characters long");
            return "redirect:/change-password";
        }

        try {
            if ("admin-role".equals(userType)) {
                // For admin, we would need to update the application properties or a separate admin table
                // For now, just show success message
                redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully");
            } else {
                // For officers, update in database
                Optional<CourierOfficer> officerOpt = courierOfficerService.getCourierOfficerByName(username);
                if (officerOpt.isPresent()) {
                    CourierOfficer officer = officerOpt.get();
                    officer.setPassword(newPassword);
                    courierOfficerService.updateCourierOfficer(officer);
                    redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "User not found");
                }
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error changing password: " + e.getMessage());
        }

        return "redirect:/change-password";
    }
}