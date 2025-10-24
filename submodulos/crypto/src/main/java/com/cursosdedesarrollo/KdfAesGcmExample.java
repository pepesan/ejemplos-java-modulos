package com.cursosdedesarrollo;

import javax.crypto.Cipher;
import javax.crypto.KDF;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.HKDFParameterSpec;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HexFormat;
import java.util.Arrays;

public class KdfAesGcmExample {
    public static void main(String[] args) throws Exception {
        // ===== 1) Material de entrada para la KDF (ejemplo) =====
        byte[] ikm  = "clave-secreta-base".getBytes();         // IKM: material inicial
        byte[] salt = "sal-unica-por-contexto".getBytes();     // Salt (debe ser impredecible por contexto)
        byte[] info = "contexto-aes-256".getBytes();           // Info/label (describe el propósito)

        // ===== 2) Instanciamos la KDF HKDF-SHA256 (JDK 25, API estándar) =====
        KDF hkdf = KDF.getInstance("HKDF-SHA256");

        // Modo Extract-then-Expand: pedimos 32 bytes (256 bits) para AES-256
        AlgorithmParameterSpec params = HKDFParameterSpec.ofExtract()
                .addIKM(ikm)
                .addSalt(salt)
                .thenExpand(info, 32);

        // Derivamos una SecretKey de tipo AES
        SecretKey aesKey = hkdf.deriveKey("AES", params);
        System.out.println("Clave AES-256 derivada: " +
                HexFormat.of().formatHex(aesKey.getEncoded()));

        // ===== 3) Preparamos AES-GCM =====
        // - IV/nonce recomendado: 12 bytes aleatorios por mensaje.
        byte[] iv = new byte[12];
        SecureRandom sr = SecureRandom.getInstanceStrong();
        sr.nextBytes(iv);

        // AAD opcional (datos autenticados pero no cifrados)
        byte[] aad = "metadatos-opcionales".getBytes();

        // Texto a cifrar (UTF-8)
        byte[] plaintext = "Mensaje ultra secreto 👀".getBytes();

        // ===== 4) Cifrado AES-GCM =====
        Cipher enc = Cipher.getInstance("AES/GCM/NoPadding");
        enc.init(Cipher.ENCRYPT_MODE, aesKey, new GCMParameterSpec(128, iv)); // tag 128 bits
        enc.updateAAD(aad); // opcional
        byte[] ciphertext = enc.doFinal(plaintext);

        // Empaquetamos como: IV || CIPHERTEXT+TAG  (formato típico “on-the-wire”)
        byte[] packet = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, packet, 0, iv.length);
        System.arraycopy(ciphertext, 0, packet, iv.length, ciphertext.length);

        System.out.println("Paquete (IV || cipher+tag): " +
                HexFormat.of().formatHex(packet));

        // ===== 5) Descifrado =====
        // Recuperamos IV y ciphertext+tag
        byte[] iv2  = Arrays.copyOfRange(packet, 0, 12);
        byte[] ct2  = Arrays.copyOfRange(packet, 12, packet.length);

        Cipher dec = Cipher.getInstance("AES/GCM/NoPadding");
        dec.init(Cipher.DECRYPT_MODE, aesKey, new GCMParameterSpec(128, iv2));
        dec.updateAAD(aad); // debe coincidir con el usado en cifrado
        byte[] recovered = dec.doFinal(ct2);

        // ===== 6) Mostramos el texto descifrado =====
        String recoveredText = new String(recovered);
        System.out.println("Texto descifrado: " + recoveredText);

        // (Comprobación simple)
        System.out.println("¿Coincide? " + Arrays.equals(plaintext, recovered));
    }
}
