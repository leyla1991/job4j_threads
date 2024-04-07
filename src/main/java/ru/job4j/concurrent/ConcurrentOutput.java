package ru.job4j.concurrent;

public class ConcurrentOutput {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread another = new Thread(
                    () -> System.out.println(Thread.currentThread().getName())
            );
            another.start();
            System.out.println(Thread.currentThread().getName());

            Thread second = new Thread(
                    () -> System.out.println(Thread.currentThread().getName())
            );
            second.start();
        }
    }
}
