package com.personal.security.password.recovery;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRecoveryTokenRepository extends JpaRepository<PasswordRecoveryToken, Integer> {

    PasswordRecoveryToken findByToken(String token);
}
