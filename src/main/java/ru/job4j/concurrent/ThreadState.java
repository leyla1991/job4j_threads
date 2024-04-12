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
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getName()
                    + " "
                    + second.getName());
        }
        String ls = System.lineSeparator();
        System.out.println(first.getName() + ": "
                + first.getState() + ls
                + second.getName() + ": "
                + second.getState() + ls
                + "Работа завершена");
    }
}
