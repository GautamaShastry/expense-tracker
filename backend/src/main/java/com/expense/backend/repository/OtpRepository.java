package com.expense.backend.repository;

import com.expense.backend.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByEmailAndVerifiedFalse(String email);
    Optional<Otp> findByEmailAndOtpCodeAndVerifiedFalse(String email, String otpCode);
}
