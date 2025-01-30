package com.personal.security.password.recovery;

import com.personal.security.password.recovery.dto.ForgotPasswordResponse;
import com.personal.security.user.User;
import com.personal.security.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PasswordRecoveryService {

    private static final String MAIL_FROM = "hs.sider@gmail.com";
    private static final String MAIL_SUBJECT = "eCommerce - Password Reset";
    private static final String MAIL_TEXT = """
            Hello,
            
            Please click on this link to reset your password: %s
            
            Regards
            eCommerce""";
    private static final String RESET_ENDPOINT_URL = "http://localhost:8080/api/v1/password/reset/";

    private final UserService userService;
    private final PasswordRecoveryTokenRepository passwordRecoveryTokenRepository;
    private final JavaMailSender javaMailSender;

    public ForgotPasswordResponse forgotPassword(User user) {
        User userFound = userService.findByEmail(user.getEmail());

        String resetPasswordUrl = RESET_ENDPOINT_URL + generateResetToken(userFound);
        sendEmail(userFound, resetPasswordUrl);

        return ForgotPasswordResponse.builder()
                .resetPasswordUrl(resetPasswordUrl)
                .build();
    }

    public boolean resetPassword(String resetToken, User user) {
        PasswordRecoveryToken reset = passwordRecoveryTokenRepository.findByToken(resetToken);
        if (reset != null && hasExpired(reset.getExpirationDateTime())) {
            userService.updatePassword(reset.getUser().getEmail(), user.getPassword());
            return true;
        }
        return false;
    }

    private boolean hasExpired(LocalDateTime expiryDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(currentDateTime);
    }

    private void sendEmail(User user, String resetLink) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(MAIL_FROM);
        msg.setTo(user.getEmail());
        msg.setSubject(MAIL_SUBJECT);
        msg.setText(String.format(MAIL_TEXT, resetLink));

        javaMailSender.send(msg);
    }

    private String generateResetToken(User user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(30);

        PasswordRecoveryToken resetToken = new PasswordRecoveryToken();
        resetToken.setToken(uuid.toString());
        resetToken.setExpirationDateTime(expiryDateTime);
        resetToken.setUser(user);
        passwordRecoveryTokenRepository.save(resetToken);

        return resetToken.getToken();
    }
}
