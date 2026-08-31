/*
package com.inventoryservice.services.jwt;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Service
public class TokenEncryptionService {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int IV_LENGTH = 12;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private String key;

    private SecretKeySpec secretKey;

    @PostConstruct
    public void init() {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalStateException("Token encryption key is not configured. "
                            + "Set security.token-encryption-key property.");
        }
        byte[] keyBytes;
        try {
            this.key = generateKey();
            keyBytes = Base64.getDecoder().decode(key.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Token encryption key is not valid Base64: " + e.getMessage(), e);
        }

        if (keyBytes.length != 32) {
            throw new IllegalArgumentException("AES-256 key must be exactly 32 bytes (256 bits). "
                            + "Provided key is " + keyBytes.length + " bytes ("
                            + (keyBytes.length * 8) + " bits).");
        }
        this.secretKey = new SecretKeySpec(keyBytes, "AES");
        Arrays.fill(keyBytes, (byte) 0);
        System.out.println("TokenEncryptionService initialized successfully");
    }

    public String encrypt(String plainText) {

        if (plainText == null) {
            throw new IllegalArgumentException("plainText cannot be null");
        }

        if (plainText.isEmpty()) {
            System.out.println("Encrypting empty string — " + "verify this is intentional");
        }

        if (secretKey == null) {
            throw new IllegalStateException("Encryption key not initialized. " + "Ensure @PostConstruct ran successfully.");
        }

        try {

            byte[] iv = new byte[IV_LENGTH];
            SECURE_RANDOM.nextBytes(iv);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            byte[] result = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);
            return Base64.getEncoder().encodeToString(result);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException("AES/GCM/NoPadding algorithm not available on this JVM", e);

        } catch (InvalidKeyException e) {
            throw new IllegalStateException("Invalid encryption key — key may be corrupted", e);

        } catch (InvalidAlgorithmParameterException e) {
            throw new IllegalStateException("Invalid GCM parameters", e);

        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalStateException("Encryption failed for provided plaintext", e);
        }
    }

    public String decrypt(String encryptedText) {

        if (encryptedText == null) {
            throw new IllegalArgumentException("encryptedText cannot be null");
        }

        if (encryptedText.trim().isEmpty()) {
            throw new IllegalArgumentException("encryptedText cannot be empty");
        }

        if (secretKey == null) {
            throw new IllegalStateException("Encryption key not initialized. " + "Ensure @PostConstruct ran successfully.");
        }

        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(encryptedText.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("encryptedText is not valid Base64", e);
        }

        int minLength = IV_LENGTH + (GCM_TAG_LENGTH / 8);
        if (decoded.length < minLength) {
            throw new IllegalArgumentException("Encrypted data is too short to be valid. "
                            + "Expected at least " + minLength + " bytes, "
                            + "got " + decoded.length + " bytes. "
                            + "Data may be corrupt or truncated.");
        }

        try {
            byte[] iv = new byte[IV_LENGTH];
            System.arraycopy(decoded, 0, iv, 0, IV_LENGTH);
            byte[] encrypted = new byte[decoded.length - IV_LENGTH];
            System.arraycopy(decoded, IV_LENGTH, encrypted, 0, encrypted.length);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (AEADBadTagException e) {
            System.out.println("GCM authentication tag verification failed " + "— data may be tampered or wrong key used");
            throw new IllegalStateException("Token decryption failed — " + "data integrity check failed", e);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException("AES/GCM/NoPadding algorithm not available on this JVM", e);

        } catch (InvalidKeyException e) {
            throw new IllegalStateException("Invalid decryption key — key may be corrupted", e);

        } catch (InvalidAlgorithmParameterException e) {
            throw new IllegalStateException("Invalid GCM parameters during decryption", e);

        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalStateException("Decryption failed — ciphertext may be corrupt", e);
        }
    }

    */
/**
     * Utility method to generate a new random AES-256 key
     * Use this to generate the value for security.token-encryption-key
     * <p>
     * Example usage:
     * String key = TokenEncryptionService.generateKey();
     * // Add to application.properties:
     * // security.token-encryption-key=<output>
     *//*

    public static String generateKey() {
        byte[] key = new byte[32];
        SECURE_RANDOM.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}*/
