package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {

        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        try {
            Thread.sleep(100);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.fillInStackTrace();
        }
        System.out.println(first.getState() + " "
                + second.getState()
                + " работа завершена");
    }
}
