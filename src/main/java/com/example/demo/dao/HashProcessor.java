package com.example.demo.dao;

import model.Person;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class HashProcessor {
    private static final int ITERATIONS_NUMBER = 1;

    public static String generateStrongPasswordHash(String password) {
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();
        PBEKeySpec spec = new PBEKeySpec(chars, salt, ITERATIONS_NUMBER, 64 * 8);
        SecretKeyFactory skf = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = new byte[0];
        try {
            hash = skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return ITERATIONS_NUMBER + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] getSalt() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public static boolean validatePassword(String originalPassword, String storedPassword) {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] testHash = new byte[0];
        try {
            testHash = skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public static void main(String[] args) {
        HashProcessor hp = new HashProcessor();
        String el = hp.generateStrongPasswordHash("1");
        System.out.println(el);
        System.out.println(hp.validatePassword("1","1:67e91145be3c24db35342986b1462fff:e777717bbd0835e46dbb2ca957124aa4444330f90bcdf13d152bca2914f1ef47c6264a9f854c61ab0e5aa30ea77030a702d3ce730b245c04ab466be85f1b3439"));
    }
}
