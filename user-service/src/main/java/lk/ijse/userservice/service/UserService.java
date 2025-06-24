package lk.ijse.userservice.service;

import lk.ijse.userservice.entity.Booking;
import lk.ijse.userservice.entity.User;
import lk.ijse.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Map<Long, List<Booking>> bookingHistory = new ConcurrentHashMap<>();
    private final List<String> activityLogs = new CopyOnWriteArrayList<>();

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        logEvent("User registered: " + savedUser.getUsername());
        return savedUser;
    }

    public User login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            logEvent("User logged in: " + username);
            return user;
        } else {
            logEvent("Failed login attempt for username: " + username);
            throw new RuntimeException("Invalid credentials");
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
        logEvent("User deleted with ID: " + id);
    }

    public User update(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) {
                existingUser.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getRole() != null && !updatedUser.getRole().isEmpty()) {
                existingUser.setRole(updatedUser.getRole());
            }
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            User savedUser = userRepository.save(existingUser);
            logEvent("User updated: " + savedUser.getUsername());
            return savedUser;
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Booking> getBookingHistory(Long userId) {
        return bookingHistory.getOrDefault(userId, Collections.emptyList());
    }

    public void addBooking(Long userId, Booking booking) {
        bookingHistory.computeIfAbsent(userId, k -> new ArrayList<>()).add(booking);
        logEvent("Booking added for user " + userId + ": " + booking);
    }

    public void logEvent(String message) {
        String timestamped = LocalDateTime.now() + " - " + message;
        activityLogs.add(timestamped);
        System.out.println(timestamped);
    }

    public List<String> getLogs() {
        return activityLogs;
    }
}
