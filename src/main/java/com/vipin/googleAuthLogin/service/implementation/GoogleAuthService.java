package com.vipin.googleAuthLogin.service.implementation;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleAuthService {

    // Generate a QR code image in Base64 format
    public static BufferedImage generateQRImage(String qrCodeText) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 200, 200, hintMap);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);

        } catch (WriterException e) {
            throw new RuntimeException("Failed to generate QR code");
        }
    }

    // Generate a new TOTP key
    public String generateKey() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        final GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }

    // Validate the TOTP code
    public boolean isValid(String secret, int code) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator(
                new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder().build()
        );
        return gAuth.authorize(secret, code);
    }

    // Generate a QR code URL for Google Authenticator
    public BufferedImage generateQRImage(String secret, String emailId) {
        String url = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL(
                "GoogleAuthLogin",
                emailId,
                new GoogleAuthenticatorKey.Builder(secret).build());
        return generateQRImage(url);
    }
}
