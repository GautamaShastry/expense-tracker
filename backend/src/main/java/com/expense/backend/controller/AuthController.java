package com.expense.backend.controller;

import com.expense.backend.dto.AuthResponseDto;
import com.expense.backend.dto.LoginRequestDto;
import com.expense.backend.dto.OtpVerificationDto;
import com.expense.backend.dto.RegisterRequestDto;
import com.expense.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto request) {
        String response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDto request) {
        Map<String, Object> response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<AuthResponseDto> verifyOtp(@RequestBody OtpVerificationDto request) {
        AuthResponseDto response = authService.verifyOtp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<Map<String, String>> resendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String message = authService.resendOtp(email);

        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        response.put("email", email);

        return ResponseEntity.ok(response);
    }
}
