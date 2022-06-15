package org.devgraft.support.provider;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class MD5Provider {
    public String encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger i = new BigInteger(1, md.digest(text.getBytes(StandardCharsets.UTF_8)));
            return String.format("%032x", i);
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
