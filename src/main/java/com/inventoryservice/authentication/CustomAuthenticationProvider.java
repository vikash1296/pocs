package com.inventoryservice.authentication;


import com.inventoryservice.services.CustomUserDetailsService;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("CustomAuthenticationProvider.authenticate() invoked...");
        String username = authentication.getName();
        String password = Objects.requireNonNull(authentication.getCredentials()).toString();

        if (username == null || username.isBlank()) {
            throw new BadCredentialsException("Username is required");
        }

        // Demo admin user
        if ("admin".equalsIgnoreCase(username) && "admin123".equals(password)) {
            System.out.println("creating session for admin user.....");
            UserDetails adminUser = User.builder()
                    .username("admin")
                    .password("")
                    .roles("ADMIN","USER","SUPER_ADMIN")
                    .build();

            return new UsernamePasswordAuthenticationToken(adminUser, null,
                    adminUser.getAuthorities());
        }

        // Load user details from DB
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        System.out.println("userDetails = " + userDetails);
        // Validate password
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // Authentication successful
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(@NonNull Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
