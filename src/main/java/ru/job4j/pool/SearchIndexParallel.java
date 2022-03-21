package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchIndexParallel<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T searchObj;
    private final int start;
    private final int end;
    private int index = -1;

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
    protected Integer compute() {
        int objLimit = 10;
        if (end - start <= objLimit) {
            for (int i = start; i <= end; i++) {
                if (array[i].equals(searchObj)) {
                    index = i;
                    break;
                }
            }
            return index;
        }
        int endPartOne = (end - start) / 2 + start;
        SearchIndexParallel<T> leftTask = new SearchIndexParallel<>(array, searchObj, start, endPartOne);
        leftTask.fork();
        SearchIndexParallel<T> rightTask = new SearchIndexParallel<>(array, searchObj, endPartOne + 1, end);
        int rightResult = rightTask.compute();
        int leftResult = leftTask.join();
        return Math.max(leftResult, rightResult);
    }

    public Integer search() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new SearchIndexParallel<>(array, searchObj));
    }
}
