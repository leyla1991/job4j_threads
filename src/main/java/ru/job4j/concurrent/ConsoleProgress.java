package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        var process = new char[] {'-', '\\', '\\', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (Character i : process) {
                    Thread.sleep(500);
                    System.out.print("\r load: " + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000);
            progress.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
