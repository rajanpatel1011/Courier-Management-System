package com.courier.management.controller;

import com.courier.management.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/reports", "/report"})
@RequiredArgsConstructor
public class ReportController {

    private final CourierService courierService;

    @GetMapping
    public String showReportMenu() {
        return "report";
    }

    @GetMapping("/datewise")
    public String showDatewiseReport(Model model) {
        model.addAttribute("couriers", courierService.getPendingCouriers());
        return "datewise-list";
    }
}
