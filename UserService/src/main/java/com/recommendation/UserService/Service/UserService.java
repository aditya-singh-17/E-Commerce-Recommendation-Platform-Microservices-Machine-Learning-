package com.recommendation.UserService.Service;

import org.springframework.stereotype.Service;

import com.recommendation.UserService.dto.LoginRequest;
import com.recommendation.UserService.dto.UserRequest;
import com.recommendation.UserService.dto.UserResponse;
import com.recommendation.UserService.entity.User;
import com.recommendation.UserService.repo.UserRepository;
import com.recommendation.UserService.util.JwtUtil;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public UserResponse register(UserRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Plain password for testing
        user.setPassword(request.getPassword());

        user.setRole("USER");

        User saved = userRepository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail()
        );
    }

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!request.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}