package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int k = 0; k < n; k++) {
            Sums sum = new Sums();
            for (int z = 0; z < n; z++) {
                sum.setRowSum(sum.getRowSum() + matrix[k][z]);
                sum.setColSum(sum.getColSum() + matrix[z][k]);
            }
            sums[k] = sum;
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        Map<Integer, CompletableFuture<Sums>> futureMap = new HashMap<>();
        for (int k = 0; k < n; k++) {
            futureMap.put(k, getTask(matrix, n, k));
        }
        for (Integer key : futureMap.keySet()) {
            sums[key] = futureMap.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int endRow, int startCol) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            for (int i = 0; i < endRow; i++) {
                    sums.setRowSum(sums.getRowSum() + matrix[startCol][i]);
                    sums.setColSum(sums.getColSum() + matrix[i][startCol]);
                }
            return sums;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sums1 = asyncSum(matrix);
        for (Sums sums : sums1) {
            System.out.println("Сумма столбца: " + sums.getColSum()
                    + " Сумма строки: " + sums.getRowSum());
        }
        Sums[] sums = sum(matrix);
        for (Sums s : sums) {
            System.out.println("Сумма столбца: " + s.getColSum()
                    + " Сумма строки: " + s.getRowSum());
        }
    }
}