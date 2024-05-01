package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    @Test
    public void whenCountDo() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread = new Thread(casCount::increment);
        Thread thread1 = new Thread(casCount::increment);
        thread.start();
        thread1.start();
        thread.join();
        thread1.join();
        int expected = 2;
        assertThat(casCount.get()).isEqualTo(expected);
    }

    @Test
    public void whenMultiCount() throws InterruptedException {
        CASCount count = new CASCount();
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);
        Thread third = new Thread(count::increment);
        Thread four = new Thread(count::increment);
        first.start();
        second.start();
        third.start();
        four.start();
        first.join();
        second.join();
        third.join();
        four.join();
        assertThat(count.get()).isEqualTo(4);
    }
}