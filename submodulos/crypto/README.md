# crypto — Criptografía moderna

**Java 25**

## Qué cubre

Derivación de claves con HKDF y cifrado autenticado con AES-GCM usando la nueva API estándar `KDF` introducida en Java 25 (JEP 478).

## Flujo del ejemplo

```
Material base (IKM) + Salt + Info
         ↓ HKDF-SHA256 (KDF.getInstance)
    Clave AES-256 (SecretKey)
         ↓ AES/GCM/NoPadding + IV aleatorio + AAD
    Texto cifrado + tag GCM
         ↓ Descifrado con la misma clave + IV + AAD
    Texto original recuperado
```

## Conceptos cubiertos

| Concepto | Descripción |
|----------|-------------|
| `KDF` API | Nueva API estándar para derivación de claves (Java 25) |
| HKDF-SHA256 | Algoritmo Extract-then-Expand basado en HMAC-SHA256 |
| AES-GCM | Cifrado autenticado: confidencialidad + integridad en un solo paso |
| `GCMParameterSpec` | Configura IV (12 bytes) y tamaño del tag de autenticación (128 bits) |
| AAD | Datos autenticados pero no cifrados (metadatos verificables) |
| `SecureRandom.getInstanceStrong()` | Generador de IV criptográficamente seguro |
| Formato IV‖ciphertext | Patrón estándar de empaquetado para transmisión |

## Clase principal

`com.cursosdedesarrollo.KdfAesGcmExample`
