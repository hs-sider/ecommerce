package com.personal.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User createUser(User user) throws Exception {
        Date eighteenYearsAgo = new Date(System.currentTimeMillis() - (1000L*60*60*24*365*18));
        if (user.getBirthdate().after(eighteenYearsAgo)) {
            throw new Exception("User should be 18 years old or above");
        }

        var createdUser = User.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .birthdate(user.getBirthdate())
                .address(user.getAddress())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .build();

        return userRepository.save(createdUser);
    }

    public User updateUser(Integer userId, User user) {
        User updatedUser = userRepository.findById(userId).orElse(null);
        if (updatedUser == null) {
            return null;
        }

        updatedUser.setFirstname(user.getFirstname());
        updatedUser.setLastname(user.getLastname());
        updatedUser.setBirthdate(user.getBirthdate());
        updatedUser.setAddress(user.getAddress());
        updatedUser.setEmail(user.getEmail());

        return userRepository.save(updatedUser);
    }

    public Integer deleteUser(Integer userId) {
        userRepository.deleteById(userId);
        return userId;
    }

    public User findByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail).orElseThrow();
    }

    public void updatePassword(String userEmail, String newPassword) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
