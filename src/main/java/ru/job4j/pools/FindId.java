package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FindId<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T value;
    private final int left;
    private final int right;

    public FindId(T[] array, T value, int left, int right) {
        this.array = array;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    @Override
    protected Integer compute() {
        if (right - left <= 10) {
            return search();
        }
        int mid = (left + right) / 2;
        FindId<T> first = new FindId<>(array, value, left, mid);
        FindId<T> second = new FindId<>(array, value, mid, right);
        first.fork();
        second.fork();
        int indF = first.join();
        int indS = second.join();
        return Math.max(indS, indF);
    }

    public static <T> int computeFind(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
       return forkJoinPool.invoke(new FindId<>(array, value, 0, array.length));
    }

    public int search() {
        int id = -1;
        for (int i = left; i < right; i++) {
            if (value.equals(array[i])) {
                id = i;
                break;
            }
        }
        return id;
    }

    public static void main(String[] args) {
        Integer[] rs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 123, 133, 1343, 12312312};
        FindId<Integer> findId = new FindId<>(rs, 4, 0, rs.length);
        computeFind(rs, 4);
        System.out.println(findId.compute());
    }
}
