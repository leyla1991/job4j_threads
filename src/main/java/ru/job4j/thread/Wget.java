package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;
    private final int speed;
    private final String file;

    public Wget(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        try (var input = new URL(url).openStream();
        var output = new FileOutputStream(file)) {
            System.out.println("Open connection: "
                    + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[1024];
            int bytesRead;
            long bytesCount = 0;
            long downloadAt = System.currentTimeMillis();

            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                bytesCount += bytesRead;
                if (bytesCount >= speed) {
                    long d = System.currentTimeMillis() - downloadAt;
                    if (1000 > d) {
                        Thread.sleep(1000 - d);
                    }
                }
                bytesCount = 0;
                downloadAt = System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isValidURL(String url) {
        boolean rsl = false;
        try {
            new URL(url).toURI();
            rsl = true;
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public static void validation(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Wrong size args");
        }
        if (!isValidURL(args[0])) {
            throw new IllegalArgumentException("Wrong URL");
        }
        if (Integer.parseInt(args[1]) < 1) {
            throw new IllegalArgumentException("Speed is wrong");
        }
    }

    public static void main(String[] args) {
        validation(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String file = args[2];
        Thread thread = new Thread(new Wget(url, speed, file));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.fillInStackTrace();
        }
    }
}
