package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadPoolTest {

    ThreadPool threadPool = new ThreadPool();

    @Test
    public void addOneTaskToPool() throws InterruptedException {
        AtomicInteger test = new AtomicInteger(0);
        threadPool.work(test::getAndIncrement);
        threadPool.shutdown();
        assertThat(test.get(), is(1));
    }

    @Test
    public void addSeveralTasksToPool() throws InterruptedException {
        AtomicInteger test = new AtomicInteger(0);
        for (int i = 0; i < 5; i++) {
            threadPool.work(test::getAndIncrement);
        }
        threadPool.shutdown();
        assertThat(test.get(), is(5));
    }

    @Test
    public void addMoreTasksThenCPUToPool() throws InterruptedException {
        AtomicInteger test = new AtomicInteger(0);
        for (int i = 0; i < Runtime.getRuntime().availableProcessors() * 2; i++) {
            threadPool.work(test::getAndIncrement);
        }
        threadPool.shutdown();
        assertThat(test.get(), is(Runtime.getRuntime().availableProcessors() * 2));
    }
}