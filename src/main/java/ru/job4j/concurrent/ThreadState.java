package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {

        Thread first = new Thread(
                () -> { }
        );
        Thread second = new Thread(
                () -> { }
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                && second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getName()
                    + " "
                    + second.getName());
        }
        System.out.println(first.getName() + ": "
                + first.getState() + "\n"
                + second.getName() + ": "
                + second.getState() + "\n"
                + "Работа завершена");
    }
}
