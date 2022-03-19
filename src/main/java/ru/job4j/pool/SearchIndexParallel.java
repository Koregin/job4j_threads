package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class SearchIndexParallel<T> extends RecursiveAction {
    private final T[] array;
    private final T searchObj;
    private final int start;
    private final int end;
    private final static int[] INDEX = new int[]{-1};

    public SearchIndexParallel(T[] array, T obj) {
        this.array = array;
        this.searchObj = obj;
        this.start = 0;
        this.end = array.length - 1;
    }

    public SearchIndexParallel(T[] array, T obj, int start, int end) {
        this.array = array;
        this.searchObj = obj;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        int objLimit = 10;
        if (end - start <= objLimit) {
            for (int i = start; i <= end; i++) {
                if (array[i].equals(searchObj)) {
                    INDEX[0] = i;
                    break;
                }
            }
        } else {
            int endPartOne = (end - start) / 2 + start;
            SearchIndexParallel<T> onePart = new SearchIndexParallel<>(array, searchObj, start, endPartOne);
            SearchIndexParallel<T> twoPart = new SearchIndexParallel<>(array, searchObj, endPartOne + 1, end);
            onePart.fork();
            twoPart.fork();
            onePart.join();
            twoPart.join();
        }
    }

    public Integer search() {
        INDEX[0] = -1;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new SearchIndexParallel<>(array, searchObj));
        return INDEX[0];
    }
}
