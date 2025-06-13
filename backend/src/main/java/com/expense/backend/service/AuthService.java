package com.expense.backend.service;

import com.expense.backend.config.JwtUtil;
import com.expense.backend.dto.AuthResponseDto;
import com.expense.backend.dto.LoginRequestDto;
import com.expense.backend.dto.OtpVerificationDto;
import com.expense.backend.dto.RegisterRequestDto;
import com.expense.backend.entity.User;
import com.expense.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    public Map<String, Object> login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // generate and send otp
        String otpMessage = otpService.generateAndSendOtp(request.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("message", otpMessage);
        response.put("email", request.getEmail());

        return response;
    }

    public AuthResponseDto verifyOtp(OtpVerificationDto request) {
        boolean isValid = otpService.verifyOTP(request.getEmail(), request.getOtpCode());

        if(!isValid) {
            throw new RuntimeException("Invalid OTP");
        }

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponseDto(user.getUsername(), user.getEmail(), token);
    }

    public String register(RegisterRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    public String resendOtp(String email) {
        userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return otpService.generateAndSendOtp(email);
    }
}
