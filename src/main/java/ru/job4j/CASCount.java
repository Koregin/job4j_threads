package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        boolean incSuccessful = false;
        while (!incSuccessful) {
            Integer value = this.count.get();
            Integer newValue = value + 1;
            incSuccessful = this.count.compareAndSet(value, newValue);
        }
    }

    public int get() {
        return this.count.get();
    }
}