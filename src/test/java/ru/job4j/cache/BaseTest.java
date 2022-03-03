package ru.job4j.cache;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BaseTest {

    @Test
    public void whenAddWithSuccess() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        assertTrue(cache.add(base1));
    }

    @Test
    public void whenDeleteWithSuccess() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        cache.add(base1);
        cache.delete(base1);
    }

    @Test
    public void whenDeleteNotExistElement() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        cache.delete(base1);
    }

    @Test
    public void whenUpdateSuccess() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        base1.setName("base1");
        cache.add(base1);
        Base base1update = new Base(1, 0);
        base1update.setName("base1update");
        assertTrue(cache.update(base1update));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateWithDifferentVersions() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        base1.setName("base1");
        cache.add(base1);
        Base base1update = new Base(1, 1);
        base1update.setName("base1update");
        cache.update(base1update);
    }
}