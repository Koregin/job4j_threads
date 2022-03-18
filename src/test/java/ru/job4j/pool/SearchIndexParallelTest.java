package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SearchIndexParallelTest {

    @Test
    public void whenSearchIndexInArrayIntegers() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        SearchIndexParallel<Integer> searchIndexParallel = new SearchIndexParallel<>(arr, 8);
        assertThat(searchIndexParallel.search(), is(7));
    }

    @Test
    public void whenSearchIndexInArrayIntegersNotFound() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        SearchIndexParallel<Integer> searchIndexParallel = new SearchIndexParallel<>(arr, 25);
        assertThat(searchIndexParallel.search(), is(-1));
    }

    @Test
    public void whenSearchIndexInArrayCharacters() {
        Character[] arr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l'};
        SearchIndexParallel<Character> searchIndexParallel = new SearchIndexParallel<>(arr, 'j');
        assertThat(searchIndexParallel.search(), is(8));
    }

    @Test
    public void whenSearchIndexInArrayCharactersNotFound() {
        Character[] arr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l'};
        SearchIndexParallel<Character> searchIndexParallel = new SearchIndexParallel<>(arr, 'z');
        assertThat(searchIndexParallel.search(), is(-1));
    }

    @Test
    public void whenSearchIndexInArrayString() {
        String[] arr = {"A", "ForkJoinPool", "provides", "the", "entry", "point", "for", "submissions", "from", ",non-ForkJoinTask", "clients"};
        SearchIndexParallel<String> searchIndexParallel = new SearchIndexParallel<>(arr, "ForkJoinPool");
        assertThat(searchIndexParallel.search(), is(1));
    }

    @Test
    public void whenSearchIndexInArrayStringNotFound() {
        String[] arr = {"A", "ForkJoinPool", "provides", "the", "entry", "point", "for", "submissions", "from", ",non-ForkJoinTask", "clients"};
        SearchIndexParallel<String> searchIndexParallel = new SearchIndexParallel<>(arr, "lambda");
        assertThat(searchIndexParallel.search(), is(-1));
    }
}