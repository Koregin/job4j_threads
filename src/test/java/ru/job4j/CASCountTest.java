package ru.job4j;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenOneIncrement() {
        CASCount casCount = new CASCount(0);
        casCount.increment();
        assertThat(1, is(casCount.get()));
    }

    @Test
    public void whenSeveralIncrements() {
        CASCount casCount = new CASCount(0);
        for (int i = 0; i < 5; i++) {
            casCount.increment();
        }
        assertThat(5, is(casCount.get()));
    }
}