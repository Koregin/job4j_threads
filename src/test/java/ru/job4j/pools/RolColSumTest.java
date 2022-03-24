package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class RolColSumTest {
    @Test
    public void whenMatrixWithSum() {
        int[][] matrix = new int[][]{{1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        RolColSum.Sums[] sums = RolColSum.sum(matrix);
        RolColSum.Sums[] testSums = new RolColSum.Sums[] {new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(24, 18)};
        assertEquals(testSums[0], sums[0]);
        assertEquals(testSums[1], sums[1]);
        assertEquals(testSums[2], sums[2]);
    }

    @Test
    public void whenMatrixWithAyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{{1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);
        RolColSum.Sums[] testSums = new RolColSum.Sums[] {new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(24, 18)};
        assertEquals(testSums[0], sums[0]);
        assertEquals(testSums[1], sums[1]);
        assertEquals(testSums[2], sums[2]);
    }
}