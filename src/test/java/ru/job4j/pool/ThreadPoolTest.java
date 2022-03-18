package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadPoolTest {

    @Test
    public void addTasksToPool() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        final Integer[] value = {0};
        for (int i = 0; i < 1000; i++) {
            threadPool.work(() -> value[0]++);
        }
        threadPool.shutdown();
        assertThat(value[0], is(1000));
    }
}