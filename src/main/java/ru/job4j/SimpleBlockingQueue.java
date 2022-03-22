package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int queueLimit;

    public SimpleBlockingQueue(int queueLimit) {
        this.queueLimit = queueLimit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        if (queue.size() == queueLimit) {
            wait();
        }
        queue.add(value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        notifyAll();
        return queue.remove();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
