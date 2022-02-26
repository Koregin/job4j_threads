package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    public List<Integer> result = new ArrayList<>();

    @Test
    public void whenOneThreadOfferAndOneThreadPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(5);

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    simpleBlockingQueue.offer(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    result.add(simpleBlockingQueue.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(result.get(0), is(5));
    }

    @Test
    public void whenOneThreadSeveralOffer() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(5);

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 6; i++) {
                    try {
                        simpleBlockingQueue.offer(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 6; i++) {
                    try {
                        result.add(simpleBlockingQueue.poll());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
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
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}