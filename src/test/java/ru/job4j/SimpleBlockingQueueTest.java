package ru.job4j;

import org.junit.jupiter.api.Test;


import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    void threads() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread consumer = new Thread(
                () -> IntStream.range(1, 5).forEach((i) -> {
                   try {
                       queue.offer(i);
                   } catch (InterruptedException e) {
                       System.out.println(Thread.currentThread().getName());
                   }
                }
        ));

        Thread producer = new Thread(
                () -> IntStream.range(1, 4).forEach((i) -> {
                    try {
                        queue.poll();
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName());
                    }
                }
                ));
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        assertThat(queue.poll()).isEqualTo(4);
    }

    @Test
    void whenPollAndOfferThenGetIt() throws InterruptedException {
        CopyOnWriteArrayList<Integer> rsl = new CopyOnWriteArrayList<>();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread consumer = new Thread(
                () -> IntStream.range(1, 10).forEach((i) -> {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
                ));

        Thread producer = new Thread(
                () -> IntStream.range(1, 9).forEach((i) -> {
                    try {
                        rsl.add(queue.poll());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
        }
        ));
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        assertThat(rsl).isNotNull().containsExactly(1, 2, 3, 4, 5, 6, 7, 8);
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
            () ->
                IntStream.range(0, 5).forEach(
                        value -> {
                            try {
                                queue.offer(value);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ));
        producer.start();
        Thread consumer = new Thread(
            () -> {
                while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                    try {
                        buffer.add(queue.poll());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }
    );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
    assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
}

    @Test
    void whenCatchLimit() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> IntStream.range(0, 10).forEach((i -> {
                    try {
                        queue.offer(i);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        )));
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }
}