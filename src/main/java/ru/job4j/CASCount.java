package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int rsl;
        int exp;
        do {
            exp = get();
            rsl = exp + 1;
        } while (!count.compareAndSet(exp, rsl));
    }

    public int get() {
        return count.get();
    }
}