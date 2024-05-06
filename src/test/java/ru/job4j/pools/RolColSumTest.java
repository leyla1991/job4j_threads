package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RolColSumTest {

    @Test
    public void matrixSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] s = RolColSum.asyncSum(matrix);
        assertThat(s[0].getRowSum()).isEqualTo(6);
        assertThat(s[0].getColSum()).isEqualTo(12);
    }

    @Test
    public void equalMatrix() throws ExecutionException, InterruptedException {
        int [][] matrix = {{1, 2}, {3, 4}};
        Sums[] s = RolColSum.asyncSum(matrix);
        Sums sums1 = new Sums();
        sums1.setColSum(4);
        sums1.setRowSum(3);
        Sums sums2 = new Sums();
        sums2.setRowSum(7);
        sums2.setColSum(6);
        Sums[] exp = new Sums[] {sums1, sums2};
        assertThat(s[0].equals(exp[0])).isEqualTo(true);
        assertThat(s[1].equals(exp[1])).isEqualTo(true);
    }

}