package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadPoolTest {

    @Test
    public synchronized void addTasksToPool() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        AtomicInteger value = new AtomicInteger(0);

        for (int i = 0; i < 100000; i++) {
            threadPool.work(value::getAndIncrement);
        }
        Thread.sleep(100);
        assertThat(value.get(), is(100000));
    }

    @Test
    public void addTaskToPoolWithPoolStop() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        AtomicInteger value = new AtomicInteger(0);

        for (int i = 0; i < 100; i++) {
            threadPool.work(value::getAndIncrement);
            Thread.sleep(10);
        }
        threadPool.shutdown();
        assertThat(value.get(), is(100));
    }
}