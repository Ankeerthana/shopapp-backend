package com.shopapp.controller;

import com.shopapp.dto.*;
import com.shopapp.entity.User;
import com.shopapp.repository.UserRepository;
import com.shopapp.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final UserRepository    userRepo;
    private final PasswordEncoder   passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtils          jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            if (userRepo.existsByEmail(req.getEmail()))
                return ResponseEntity.badRequest().body("Email already in use");

            User user = User.builder()
                    .email(req.getEmail())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .firstName(req.getFirstName())
                    .lastName(req.getLastName())
                    .role(User.Role.USER)
                    .build();

            userRepo.save(user);
            return ResponseEntity.ok("Registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            String token = jwtUtils.generateToken(req.getEmail());
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }
}
