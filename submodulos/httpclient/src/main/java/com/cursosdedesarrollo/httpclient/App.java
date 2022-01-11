package com.cursosdedesarrollo.httpclient;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
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
    public static void main( String[] args )
            throws IOException, InterruptedException, ExecutionException, TimeoutException {
        System.out.println( "Ejemplos de HttpClient!" );
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://httpbin.org/get"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print response headers
        HttpHeaders headers = response.headers();
        headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

        // Async request

        CompletableFuture<HttpResponse<String>> response2 =
                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        String result = response2.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

        System.out.println(result);


        Map<Object, Object> data = new HashMap<>();
        data.put("username", "abc");
        data.put("password", "123");
        data.put("custom", "secret");
        data.put("ts", System.currentTimeMillis());

        HttpRequest request3 = HttpRequest.newBuilder()
                .POST(ofFormData(data))
                .uri(URI.create("https://httpbin.org/post"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response3 = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response3.statusCode());

        // print response body
        System.out.println(response3.body());


        // Envio de JSON
        String json = new StringBuilder()
                .append("{")
                .append("\"name\":\"pepesan\",")
                .append("\"notes\":\"colega donde esta tu coche\"")
                .append("}").toString();


        HttpRequest request4 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create("https://httpbin.org/post"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response4 = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response4.statusCode());

        // print response body
        System.out.println(response4.body());

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
