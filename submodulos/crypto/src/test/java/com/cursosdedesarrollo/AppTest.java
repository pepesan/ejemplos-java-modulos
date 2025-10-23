package com.cursosdedesarrollo;


import org.junit.jupiter.api.Test;
import javax.crypto.KDF;
import javax.crypto.KEM;
import javax.crypto.SecretKey;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.HKDFParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.NamedParameterSpec;
import java.util.Base64;

public class AppTest {

    // Java 25
    @Test
    void testCosa() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        byte[] ikm = "secret".getBytes(StandardCharsets.UTF_8);
        byte[] salt = "salt".getBytes(StandardCharsets.UTF_8);

        // Nuevo algoritmo KDF (Key Derivation Function)
        // También se han añadido PBKDF2 y Scrypt
        KDF kdf = KDF.getInstance("HKDF-SHA256");

        AlgorithmParameterSpec params =
                HKDFParameterSpec.ofExtract()
                        .addIKM(ikm)
                        .addSalt(salt)
                        .thenExpand("context".getBytes(StandardCharsets.UTF_8), 32); // 32 bytes = 256 bits

        SecretKey key = kdf.deriveKey("AES", params);

        System.out.println("Clave (Original): secret");
        System.out.println("Salt (Original): salt");
        // Algoritmo / formato / longitud (ya tenías)
        System.out.println("Algorithm: " + key.getAlgorithm());
        System.out.println("Format: " + key.getFormat());
        System.out.println("Encoded length (bytes): " + key.getEncoded().length);

        // --- Clave en Base64 (forma legible y común) ---
        String base64 = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Clave (Base64): " + base64);

        // --- Clave en HEX (representación "sin formato") ---
        System.out.println("Clave (HEX): " + bytesToHex(key.getEncoded()));

        // Si por alguna razón quieres ver los bytes "crudos" por println (no recomendado),
        // puedes imprimir el array; pero normalmente se usan Base64/HEX:
        System.out.println("Clave (bytes): " + java.util.Arrays.toString(key.getEncoded()));


        KeyPairGenerator kpg = KeyPairGenerator.getInstance("X25519");
        kpg.initialize(new NamedParameterSpec("X25519"));
        KeyPair kp = kpg.generateKeyPair();

        KEM kem = KEM.getInstance("X25519");
        KEM.Encapsulator enc = kem.newEncapsulator(kp.getPublic());
        KEM.Encapsulated encap = enc.encapsulate();

        byte[] secret = encap.key().getEncoded();
        System.out.println("Secret length: " + secret.length);
    }

    // Helper: convierte bytes a hex (minúsculas)
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
