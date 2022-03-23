package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SearchIndexParallelTest {

    @Test
    public void whenSearchIndexInArrayIntegers() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        assertThat(SearchIndexParallel.search(arr, 8), is(7));
    }

    @Test
    public void whenSearchIndexInArrayIntegersNotFound() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        assertThat(SearchIndexParallel.search(arr, 25), is(-1));
    }

    @Test
    public void whenSearchIndexInArrayCharacters() {
        Character[] arr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l'};
        assertThat(SearchIndexParallel.search(arr, 'j'), is(8));
    }

    @Test
    public void whenSearchIndexInArrayCharactersNotFound() {
        Character[] arr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l'};
        assertThat(SearchIndexParallel.search(arr, 'z'), is(-1));
    }

    @Test
    public void whenSearchIndexInArrayString() {
        String[] arr = {"A", "ForkJoinPool", "provides", "the", "entry", "point", "for", "submissions", "from", ",non-ForkJoinTask", "clients"};
        assertThat(SearchIndexParallel.search(arr, "ForkJoinPool"), is(1));
    }

    @Test
    public void whenSearchIndexInArrayStringNotFound() {
        String[] arr = {"A", "ForkJoinPool", "provides", "the", "entry", "point", "for", "submissions", "from", ",non-ForkJoinTask", "clients"};
        assertThat(SearchIndexParallel.search(arr, "lambda"), is(-1));
    }
}