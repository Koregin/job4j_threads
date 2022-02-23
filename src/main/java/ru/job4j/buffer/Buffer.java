package ru.job4j.buffer;

public class Buffer {
    private StringBuffer buffer = new StringBuffer();

    public synchronized void add(int value) {
        System.out.println(value);
        buffer.append(value);
    }

    public void add2(int value) {
        synchronized (this) {
            System.out.println(value);
            buffer.append(value);
        }
    }

    @Override
    public synchronized String toString() {
        return buffer.toString();
    }
}
