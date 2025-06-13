package com.expense.backend.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    @NotBlank
    @Size(min = 3, max = 100)
    private String fullName;

    @Size(max = 20)
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one digit, and one special character"
    )
    private String password;

    @NotBlank
    private String confirmPassword;

    @AssertTrue(message = "Password and Confirm Password do not match")
    public boolean isPasswordsMatch() {
        return password!=null && password.equals(confirmPassword);
    }
}
