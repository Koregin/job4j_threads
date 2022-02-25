package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    public List<Integer> result = new ArrayList<>();

    @Test
    public void whenOneThreadOfferAndOneThreadPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>();

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                simpleBlockingQueue.offer(5);
            }
        });
        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                result.add(simpleBlockingQueue.poll());
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
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>();

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 6; i++) {
                    simpleBlockingQueue.offer(i);
                }
            }
        });
        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                Integer temp;
                for (int i = 1; i < 6; i++) {
                    result.add(simpleBlockingQueue.poll());
                }
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }
}