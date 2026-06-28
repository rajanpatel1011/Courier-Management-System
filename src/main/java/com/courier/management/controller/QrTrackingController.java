package com.courier.management.controller;

import com.courier.management.config.AppProperties;
import com.courier.management.dto.TrackingStatusDto;
import com.courier.management.service.LiveTrackingService;
import com.courier.management.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class QrTrackingController {

    private final LiveTrackingService liveTrackingService;
    private final QrCodeService qrCodeService;
    private final AppProperties appProperties;

    /** Mobile-friendly live tracking page (opened when a QR code is scanned). */
    @GetMapping("/track/q/{consignmentNo}")
    public String liveTrackingPage(@PathVariable String consignmentNo, Model model) {
        return liveTrackingService.getTrackingStatus(consignmentNo)
            .map(status -> {
                model.addAttribute("tracking", status);
                model.addAttribute("refreshSeconds", appProperties.getQr().getRefreshIntervalSeconds());
                model.addAttribute("qrImageUrl", "/track/q/" + status.consignmentNo() + "/qr.png");
                return "track-live";
            })
            .orElseGet(() -> {
                model.addAttribute("consignmentNo", consignmentNo);
                return "track-not-found";
            });
    }

    /** QR code image for printing on labels or showing after booking. */
    @GetMapping("/track/q/{consignmentNo}/qr.png")
    public ResponseEntity<byte[]> qrCodeImage(@PathVariable String consignmentNo) {
        if (liveTrackingService.getTrackingStatus(consignmentNo).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        byte[] png = qrCodeService.generateTrackingQrPng(consignmentNo);
        return ResponseEntity.ok()
            .header(HttpHeaders.CACHE_CONTROL, "public, max-age=3600")
            .contentType(MediaType.IMAGE_PNG)
            .body(png);
    }

    /** JSON endpoint for real-time polling from the live tracking page. */
    @GetMapping("/api/track/{consignmentNo}")
    @ResponseBody
    public ResponseEntity<TrackingStatusDto> trackingApi(@PathVariable String consignmentNo) {
        return liveTrackingService.getTrackingStatus(consignmentNo)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
