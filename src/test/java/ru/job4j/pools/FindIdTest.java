package ru.job4j.pools;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FindIdTest {

    @Test
    public void whenVariousType() {
       String[] s = {"T", "S", "P", "R", "W", "A", "G", "Q", "I", "Z", "M"};
       Integer[] array = {1, 3, 4 , 6, 7, 8, 9, 10, 23, 323, 34};
       FindId<String> stringFindId = new FindId<>(s,  "W", 0, s.length);
       FindId<Integer> arrayF = new FindId<>(array, 3, 0, array.length);
       stringFindId.computeFind(s);
       arrayF.computeFind(array);
       assertThat(arrayF.compute()).isEqualTo(1);
       assertThat(stringFindId.compute()).isEqualTo(4);
    }

    @Test
    public void whenLineFind() {
        Integer[] i = {1, 2, 3, 4};
        Integer value = 3;
        FindId<Integer> integerFindId = new FindId<>(i,  value, 0, i.length);
        integerFindId.computeFind(i);
        assertThat(integerFindId.compute()).isEqualTo(2);
    }

    @Test
    public void whenManyElements() {
        Integer[] array = new Integer[10000000];
        array[565] = 566;
        Integer value = 566;
        FindId<Integer> integerFindId = new FindId<>(array, value, 0, array.length);
        integerFindId.computeFind(array);
        assertThat(integerFindId.compute()).isEqualTo(565);
    }

    @Test
    public void whenIndexNotFound() {
        String[] array = new String[102232];
        array[1] = "S";
        FindId<String> indexFound = new FindId<>(array, "W", 0, array.length);
        indexFound.computeFind(array);
        assertThat(indexFound.compute()).isEqualTo(-1);
    }
}
