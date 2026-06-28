package com.courier.management.service;

import com.courier.management.config.AppProperties;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class QrCodeService {

    private final AppProperties appProperties;

    public String buildTrackingUrl(String consignmentNo) {
        String base = appProperties.getPublicBaseUrl().replaceAll("/$", "");
        return base + "/track/q/" + consignmentNo.trim();
    }

    public byte[] generateQrPng(String content, int size) {
        try {
            BitMatrix matrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", output);
            return output.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate QR code", e);
        }
    }

    public byte[] generateTrackingQrPng(String consignmentNo) {
        return generateQrPng(buildTrackingUrl(consignmentNo), 280);
    }
}
