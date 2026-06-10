# httpclient — Cliente HTTP moderno

**Java 11**

## Qué cubre

`java.net.http.HttpClient` reemplaza a `HttpURLConnection`. Soporta HTTP/1.1 y HTTP/2, peticiones síncronas y asíncronas, y tiene una API fluida.

## Ejemplos incluidos

| Concepto | Descripción |
|----------|-------------|
| GET síncrono | `httpClient.send(request, BodyHandlers.ofString())` |
| GET asíncrono | `httpClient.sendAsync(...)` devuelve `CompletableFuture` |
| POST con form data | Body codificado como `application/x-www-form-urlencoded` |
| POST con JSON | Body como `String` con `Content-Type: application/json` |
| Autenticación básica | `HttpClient.newBuilder().authenticator(...)` |
| Lectura de cabeceras | `response.headers().map()` |
| Timeout de conexión | `connectTimeout(Duration.ofSeconds(10))` |

## Clase principal

`com.cursosdedesarrollo.httpclient.App`
