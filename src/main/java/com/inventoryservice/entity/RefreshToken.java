package com.inventoryservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    private String refreshToken;
    private Instant expires;
    private String name;
}
