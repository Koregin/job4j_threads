package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public CASCount(Integer count) {
        this.count.set(count);
    }

    public void increment() {
        Integer value;
        int newValue;
        do {
            value = this.count.get();
            newValue = value + 1;
        } while (!this.count.compareAndSet(value, newValue));
    }

    public int get() {
        return this.count.get();
    }
}
