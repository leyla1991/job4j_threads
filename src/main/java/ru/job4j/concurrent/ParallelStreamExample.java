package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelStreamExample {

    public static void main(String[] args) {

        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7);
        Stream<Integer> stream = list.parallelStream();
        System.out.println(stream.isParallel());
        Optional<Integer> parallelOptional = stream.reduce((left, right) -> left * right);
        System.out.println(parallelOptional.get());

        IntStream intStream = IntStream.range(1, 100).parallel();
        System.out.println(intStream.isParallel());
        IntStream sequential = intStream.sequential();
        System.out.println(sequential.isParallel());

        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);

        list1.stream().parallel().peek(System.out::println).toList();
        list1.stream().parallel().forEach(System.out::println);
        list1.stream().parallel().forEachOrdered(System.out::println);
    }
}
