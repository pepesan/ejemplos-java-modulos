package com.cursosdedesarrollo.httpclient;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.util.List;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    public static void main( String[] args ) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        System.out.println( "Ejemplos de HttpClient!" );
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println( "Ejemplos de Petición Get síncrona!" );
        System.out.println( "Cabeceras!" );
        // print response headers
        HttpHeaders headers = response.headers();
        headers.map().forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println( "Status Code!" );
        // print status code
        System.out.println(response.statusCode());
        System.out.println( "Body!" );
        // print response body
        System.out.println(response.body());

        // Async request
        System.out.println( "Ejemplos de Petición Get Asíncrona!" );
        CompletableFuture<HttpResponse<String>> response2 =
                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        String result = response2
                // código a ejecutar cuando la petición se haya completado
                .thenApply(
                        // valor a devolver por parte de la función
                        // (response) -> {return response.body();}
                        // r -> {return r.body();}
                        // r -> r.body()
                        HttpResponse::body
                )
                // tiempo a esperar de timeout
                .get(5, TimeUnit.SECONDS);
        System.out.println("petición asíncrona");
        System.out.println(result);

        System.out.println( "Ejemplos de Petición Post Síncrona!" );
        // definimos el objeto que queremos mandar
        Map<Object, Object> data = new HashMap<>();
        data.put("username", "abc");
        data.put("password", "123");
        data.put("custom", "secret");
        data.put("ts", System.currentTimeMillis());

        HttpRequest request3 = HttpRequest.newBuilder()
                // defines la llamada post con datos en el body (string param1=2&param2=4)
                .POST(ofFormData(data))
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                // enviando como si fuera un formulario real
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response3 = httpClient.send(request3, HttpResponse.BodyHandlers.ofString());
        System.out.println("petición vía post con datos en body");
        // print status code
        System.out.println(response3.statusCode());

        // print response body
        System.out.println(response3.body());

        System.out.println( "Ejemplos de Petición Post JSON Síncrona!" );
        // Envio de JSON
        String json = new StringBuilder()
                .append("{")
                .append("\"name\":\"pepesan\",")
                .append("\"notes\":\"colega donde esta tu coche\"")
                .append("}").toString();


        HttpRequest request4 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                // body en formato json
                .header("Content-Type", "application/json")
                .build();
        System.out.println("petición vía post con datos JSON en body");
        HttpResponse<String> response4 = httpClient.send(request4, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response4.statusCode());

        // print response body
        System.out.println(response4.body());
        System.out.println( "Ejemplos de Petición Patch JSON Síncrona!" );
        HttpRequest request5 = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                // petición con verbo Patch
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .headers("Authentication", "Bearer token")
                .build();
        HttpResponse<String> response5 = httpClient.send(request5, HttpResponse.BodyHandlers.ofString());
        System.out.println("petición vía patch con datos JSON en body");
        // print status code
        System.out.println(response5.statusCode());

        // print response body
        System.out.println(response5.body());
        /*
        HttpClient httpClient2 = HttpClient.newBuilder()
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "user",
                                "password".toCharArray());
                    }

                })
                .connectTimeout(Duration.ofSeconds(10))
                .build();

         */
        /*
        Manejo multihilo
        var client = HttpClient.newBuilder()
            .authenticator(Authenticator.getDefault())
            .connectTimeout(Duration.ofSeconds(30))
            .cookieHandler(CookieHandler.getDefault())
            .executor(Executors.newFixedThreadPool(2))
            .followRedirects(Redirect.NEVER)
            .priority(1) //HTTP/2 priority
            .proxy(ProxySelector.getDefault())
            .sslContext(SSLContext.getDefault())
            .version(Version.HTTP_2)
            .sslParameters(new SSLParameters())
            .build();
         */

        // ================================================================
        // Peticiones múltiples de HttpClient de manera asíncrona (Java 11+)
        // ================================================================
        System.out.println("\n--- Ejemplos de Peticiones Múltiples Asíncronas ---");
        List<URI> uris = List.of(
                URI.create("https://jsonplaceholder.typicode.com/posts/1"),
                URI.create("https://jsonplaceholder.typicode.com/posts/2"),
                URI.create("https://jsonplaceholder.typicode.com/posts/3")
        );

        System.out.println("Lanzando " + uris.size() + " peticiones HTTP asíncronas concurrentes...");
        long startAsync = System.currentTimeMillis();

        List<CompletableFuture<HttpResponse<String>>> futures = uris.stream()
                .map(uri -> {
                    HttpRequest req = HttpRequest.newBuilder()
                            .GET()
                            .uri(uri)
                            .setHeader("User-Agent", "Java 11 HttpClient Bot")
                            .build();
                    // Envía cada petición de forma asíncrona
                    return httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString());
                })
                .toList();

        // Combinamos todos los CompletableFuture usando CompletableFuture.allOf.
        // Nota: allOf() requiere un varargs (que compila como array CompletableFuture<?>[]).
        // Usamos toArray(new CompletableFuture[0]) para convertir la lista a un array tipado;
        // el tamaño 0 le indica a Java el tipo y la JVM instancia un array del tamaño exacto de forma óptima.
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        // Procesamos los resultados de todas las peticiones cuando terminen
        CompletableFuture<List<String>> allResults = allOf.thenApply(v -> {
            return futures.stream()
                    .map(future -> {
                        try {
                            HttpResponse<String> resp = future.join(); // join() es seguro porque allOf garantiza finalización
                            return "  [Éxito] URI: " + resp.uri() + " | Código: " + resp.statusCode() + " | Longitud Body: " + resp.body().length() + " bytes";
                        } catch (Exception e) {
                            return "  [Error] en petición: " + e.getMessage();
                        }
                    })
                    .toList();
        });

        // Esperamos a que finalice la combinación de todos los resultados con un timeout prudente
        List<String> resultados = allResults.get(15, TimeUnit.SECONDS);
        long endAsync = System.currentTimeMillis();

        System.out.println("Resultados de las peticiones múltiples:");
        resultados.forEach(System.out::println);
        System.out.println("Peticiones concurrentes completadas en: " + (endAsync - startAsync) + " ms");

        // ================================================================
        // Peticiones múltiples procesadas al vuelo (según van llegando)
        // ================================================================
        System.out.println("\n--- Ejemplos de Peticiones Múltiples Procesadas al Vuelo ---");
        List<URI> urisAlVuelo = List.of(
                URI.create("https://jsonplaceholder.typicode.com/posts/1"),
                URI.create("https://jsonplaceholder.typicode.com/posts/2"),
                URI.create("https://jsonplaceholder.typicode.com/posts/3")
        );

        System.out.println("Lanzando " + urisAlVuelo.size() + " peticiones HTTP asíncronas independientes...");
        long startAlVuelo = System.currentTimeMillis();

        List<CompletableFuture<Void>> futurosAlVuelo = urisAlVuelo.stream()
                .map(uri -> {
                    HttpRequest req = HttpRequest.newBuilder()
                            .GET()
                            .uri(uri)
                            .setHeader("User-Agent", "Java 11 HttpClient Bot")
                            .build();

                    // sendAsync retorna un CompletableFuture. Encadenamos thenAccept para procesar
                    // la respuesta individual de forma inmediata tan pronto como finalice la petición.
                    return httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                            .thenAccept(resp -> {
                                long tiempoLlegada = System.currentTimeMillis() - startAlVuelo;
                                System.out.println("  [Llegada al vuelo - " + tiempoLlegada + " ms] URI: " + resp.uri() 
                                        + " | Código: " + resp.statusCode() 
                                        + " | Longitud: " + resp.body().length() + " bytes");
                            })
                            .exceptionally(ex -> {
                                System.err.println("  [Fallo al vuelo] Petición fallida: " + ex.getMessage());
                                return null;
                            });
                })
                .toList();

        // Para evitar que el hilo principal (main) termine antes de que las peticiones asíncronas
        // finalicen e impriman por consola, bloqueamos temporalmente esperando al grupo de futuros.
        CompletableFuture.allOf(futurosAlVuelo.toArray(new CompletableFuture[0])).join();
        System.out.println("Procesamiento al vuelo completado.");
    }

    public static HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
}
