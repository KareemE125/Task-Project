package com.ke.task.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class CryptoUtils {

    // Get the algorithm name and salt from the application.properties file
    @Value("${crypto.algoname}")
    private String appAlgorithmName;
    @Value("${crypto.salt}")
    private String appSalt;
    @Value("${crypto.convertbyte.format}")
    private String appConvertByteFormat;

    // Initialize the algorithm name and salt to be static using post constructor trigger
    private static String algorithmName;
    private static String salt;
    private static String convertByteFormat;

    @PostConstruct
    public void init() {
        algorithmName = appAlgorithmName;
        salt = appSalt;
        convertByteFormat = appConvertByteFormat;
    }

    public static String generateSHA1Value(String input) {
        try {
            // Get the instance of the Crypto algorithm
            // Add the salt to the input -> hash it -> convert it to hex string

            MessageDigest digest = MessageDigest.getInstance(algorithmName);
            input = input+salt;
            byte[] hashingBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashingBytes) {
                hexStringBuilder.append(String.format(convertByteFormat, b));
            }
            return hexStringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("The Requested Algorithm "+algorithmName+", Doesn't exist", e);
        }
    }


}
