package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    public final static int PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(5);

    private ThreadPool() throws InterruptedException {
        for (int i = 0; i < PROCESSORS; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
    }

    /*Method should add task in blocking queue*/
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /* Method should finish all started tasks */
    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 5; i++) {
            threadPool.work(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Hello from thread " + Thread.currentThread().getName());
                }
            });
        }
        threadPool.shutdown();
        System.out.println("Hello from main thread");
    }
}
