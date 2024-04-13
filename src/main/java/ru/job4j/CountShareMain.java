package ru.job4j;

public class CountShareMain {
    public static void main(String[] args) throws InterruptedException {
        Count count = new Count();
        Thread t1 = new Thread(count::increment);
        Thread t2 = new Thread(count::increment);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(count.get());
    }
}
