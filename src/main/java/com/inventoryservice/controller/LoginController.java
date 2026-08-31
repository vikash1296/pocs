package com.inventoryservice.controller;

import com.inventoryservice.authentication.CustomAuthenticationProvider;
import com.inventoryservice.dto.LoginRequest;
import com.inventoryservice.dto.LoginResponse;
import com.inventoryservice.dto.RefreshTokenRequest;
import com.inventoryservice.dto.RefreshTokenResponse;
import com.inventoryservice.entity.RefreshToken;
import com.inventoryservice.services.jwt.JwtService;
import com.inventoryservice.services.jwt.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Duration;
import java.util.Objects;


@RestController
@RequestMapping("/api")
@Slf4j
public class LoginController {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private JwtService  jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login Request from the user: {}", loginRequest.getUserName());
        try {
            Authentication authentication = customAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                            loginRequest.getPassword()));

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new BadCredentialsException("Authentication failed");
            }
            log.info("Authentication Successful,creating jwt access and refresh token...");
            //adding logic to check the is any token available for user and which not expired
            UserDetails userDetails = (UserDetails) Objects.requireNonNull(authentication.getPrincipal());
            //create access token
            String token = jwtService.generateToken(userDetails);
            //create refresh token
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
            //redisTemplate.opsForValue().set(loginRequest.getUserName(),refreshToken.toString(), Duration.ofHours(5));
            log.info("New access token & refresh token created, returning them in response...");
            return ResponseEntity.ok(new LoginResponse(token,refreshToken.getRefreshToken()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        LoginResponse loginResponse = refreshTokenService.refreshToken(request.getToken());
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }
}
