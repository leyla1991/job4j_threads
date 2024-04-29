package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private int limit;

    public SimpleBlockingQueue() {

    }

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (limit == queue.size()) {
            wait();
        }
        if (queue.isEmpty()) {
            notify();
        }
        queue.add(value);
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
        if (queue.size() == limit) {
            notify();
        }
        T item = queue.poll();
        return item;
    }

    public boolean isEmpty() {
        return queue.size() == 0;
    }
}