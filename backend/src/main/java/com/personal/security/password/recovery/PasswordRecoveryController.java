package com.personal.security.password.recovery;

import com.personal.security.password.recovery.dto.ForgotPasswordResponse;
import com.personal.security.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    @PostMapping("/forgot")
    public ForgotPasswordResponse forgot(@RequestBody User user) {
        return passwordRecoveryService.forgotPassword(user);
    }

    @PostMapping("/reset/{resetToken}")
    public String reset(@Valid @PathVariable("resetToken") String resetToken, @RequestBody User user) {

        return "Password update " + (passwordRecoveryService.resetPassword(resetToken, user) ? "success" : "failure");
    }
}
