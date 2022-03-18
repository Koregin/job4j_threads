package ru.job4j.pool;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchIndexParallel<T> extends RecursiveTask<List<Integer>> {
    private final T[] array;
    private final T searchObj;
    private final int start;
    private final int end;
    private final List<Integer> indexList = new CopyOnWriteArrayList<>();

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
    protected List<Integer> compute() {
        int objLimit = 10;
        if (end - start <= objLimit) {
            for (int i = start; i <= end; i++) {
                if (array[i].equals(searchObj)) {
                    indexList.add(i);
                    break;
                }
            }
            return indexList;
        }
        int endPartOne = end % 2 == 0 ? (end - start) / 2 + start - 1 : (end - start) / 2 + start;
        SearchIndexParallel<T> onePart = new SearchIndexParallel<>(array, searchObj, start, endPartOne);
        SearchIndexParallel<T> twoPart = new SearchIndexParallel<>(array, searchObj, endPartOne + 1, end);
        onePart.fork();
        twoPart.fork();
        indexList.addAll(onePart.join());
        indexList.addAll(twoPart.join());
        return indexList;
    }

    public Integer search() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Integer> resultList = forkJoinPool.invoke(new SearchIndexParallel<>(array, searchObj));
        return resultList.isEmpty() ? -1 : resultList.get(0);
    }
}
