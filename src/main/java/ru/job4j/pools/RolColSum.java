package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums() {
        }

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }
    /* Consistent method*/
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
        }
        for (int row = 0; row < matrix.length; row++) {
            int rowSum = 0;
            for (int col = 0; col < matrix.length; col++) {
                rowSum += matrix[row][col];
            }
            sums[row].setRowSum(rowSum);
        }
        for (int col = 0; col < matrix.length; col++) {
            int colSum = 0;
            for (int[] ints : matrix) {
                colSum += ints[col];
            }
            sums[col].setColSum(colSum);
        }
        return sums;
    }

    /*Asynchronous method*/
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = calcSum(matrix, i).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> calcSum(int[][] data, int rowColindex) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            for (int col = 0; col < data.length; col++) {
                rowSum += data[rowColindex][col];
            }
            for (int[] datum : data) {
                colSum += datum[rowColindex];
            }
            return new Sums(rowSum, colSum);
        });
    }
}
