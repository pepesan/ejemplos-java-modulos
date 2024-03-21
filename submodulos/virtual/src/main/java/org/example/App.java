package org.example;
// ref: https://failapp.dev/blog/java-virtual-threads
import java.time.Duration;
import java.time.Instant;

public class App
{
    public static void main( String[] args )
    {
        platformThreads();
        virtualThreads();
    }
    public static void platformThreads() {

        System.out.println("platform threads");
        final Instant start = Instant.now();

        for (int i =0; i < 50_000; i++) {
            if (i % 10_000 == 0) {
                System.out.println(i);
            }
            new Thread( () -> {
                try {
                    Thread.sleep(5_000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        }
        printElapsedTime( Duration.between( start, Instant.now()) );
    }
    public static void virtualThreads() {
        System.out.println("virtual threads");
        final Instant start = Instant.now();

        for (int i =0; i < 50_000; i++) {
            if (i % 10_000 == 0) {
                System.out.println(i);
            }
            Thread.startVirtualThread (() -> {
                try {
                    Thread.sleep(5_000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        printElapsedTime( Duration.between( start, Instant.now()) );
    }
    private static void printElapsedTime(Duration duration) {
        long seconds = duration.getSeconds() % 60;
        long millis = duration.toMillis() % 60;
        System.out.println( String.format("Time: %d seconds, %d milliseconds", seconds, millis) );
    }
}
