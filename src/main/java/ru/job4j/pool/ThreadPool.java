package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    public final static int PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(PROCESSORS);

    private ThreadPool() throws InterruptedException {
        for (int i = 0; i < PROCESSORS; i++) {
            work(new Work(i));
            threads.add(new Thread(tasks.poll()));
            threads.get(i).start();
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
        System.out.println("Size of threads pool: " + threadPool.threads.size());
        threadPool.shutdown();
    }
}

class Work implements Runnable {
    private int id;

    public Work(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        if (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.println("Starting thread " + Thread.currentThread().getName());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Work " + id + " was completed");
        }
    }
}
