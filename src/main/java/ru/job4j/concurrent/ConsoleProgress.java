package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        var process = new char[] {'-', '\\', '\\', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (int i = 0; i < 4; i++) {
                    Thread.sleep(500);
                    System.out.print("\r load: " + process[i]);
            }

            } catch (InterruptedException e) {
                e.fillInStackTrace();
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
            e.fillInStackTrace();
        }

    }
}
