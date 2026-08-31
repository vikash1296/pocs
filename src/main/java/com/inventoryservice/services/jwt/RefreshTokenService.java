package com.inventoryservice.services.jwt;

import com.inventoryservice.dto.LoginResponse;
import com.inventoryservice.entity.RefreshToken;
import com.inventoryservice.exceptions.InvalidRefreshTokenException;
import com.inventoryservice.exceptions.TokenNotFoundException;
import com.inventoryservice.repository.RefreshTokenRepository;
import com.inventoryservice.services.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class RefreshTokenService {

  /*  @Autowired
    private RedisTemplate<String, RefreshToken> refreshTokenRedisTemplate;*/

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public LoginResponse refreshToken(String token) {
        log.info("Inside refreshToken method..");
        RefreshToken refreshToken = validateRefreshToken(token);
        if (refreshToken == null) {
            log.error("Invalid refresh token");
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }
        String newAccessToken = jwtService.generateToken(userService.
                loadUserByUsername(refreshToken.getName()));

        log.info("After generating JWT access Token.......");
        return new LoginResponse(newAccessToken, refreshToken.getRefreshToken());
    }

    public RefreshToken createRefreshToken(String username) {
        log.info("Inside createRefreshToken method..");
        long refreshTokenExpiry = 5 * 60 * 60 * 1000;
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(UUID.randomUUID().toString())
                .expires(Instant.now().plusMillis(refreshTokenExpiry))
                .name(username)
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    private RefreshToken validateRefreshToken(String token) {
      log.info("Inside validateRefreshToken method..");
        if (token == null || token.isBlank()) {
            log.info("Refresh token is missing");
            return null;
        }
   /*     RefreshToken refreshToken =
                refreshTokenRedisTemplate.opsForValue().get(token);*/
        //fetch refresh token from db
        RefreshToken refreshToken = refreshTokenRepository.findById(token)
                .orElseThrow(() -> new TokenNotFoundException("Invalid refresh token"));

        // Verify that the supplied token matches the stored token
        if (!token.equals(refreshToken.getRefreshToken())) {
            log.info("Refresh token mismatch");
            throw new InvalidRefreshTokenException("Refresh token mismatch");
        }

        // Check refresh token expiry
        if (refreshToken.getExpires().isBefore(Instant.now())) {
            log.info("Refresh token is expired, please login again");
            //refreshTokenRedisTemplate.delete(token);
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidRefreshTokenException("Refresh token is expired");
        }
        log.info("Refresh token is valid and not expired");
        return refreshToken;
    }
}

