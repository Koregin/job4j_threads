package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(Runtime.getRuntime().availableProcessors());

    private ThreadPool() {
        System.out.println("Thread pool was created!");
    }

    private static class SingletonThreadPool {
        public static final ThreadPool THREAD_INSTANCE = new ThreadPool();
    }

    public static ThreadPool getInstance() {
        return SingletonThreadPool.THREAD_INSTANCE;
    }

    /*Method should add task in blocking queue*/
    public void work(Runnable job) {
        try {
            tasks.offer(job);
            Thread thread = new Thread(tasks.poll());
            threads.add(thread);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /* Method should finish all started tasks */
    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) {
        ThreadPool threadPool = ThreadPool.getInstance();
        for (int i = 0; i < 5; i++) {
            threadPool.work(new Work(i));
        }
        System.out.println("Size of threads pool: " + threadPool.threads.size());
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
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Work " + id + " was completed");
        }
    }
}
