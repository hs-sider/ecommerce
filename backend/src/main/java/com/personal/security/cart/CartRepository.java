package com.personal.security.cart;

import com.personal.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByStatus(Status status);
    Optional<Cart> findByStatusAndUser(Status status, User user);
}
