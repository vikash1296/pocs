package com.inventoryservice.dto;

import com.inventoryservice.entity.RefreshToken;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponse {
    private String token;
    private String refreshToken;
}
