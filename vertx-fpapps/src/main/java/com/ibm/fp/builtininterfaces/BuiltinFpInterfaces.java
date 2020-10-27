package com.ibm.fp.builtininterfaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BuiltinFpInterfaces {
    public static void main(String[] args) {
        Consumer<String> consumer = null;
        consumer = (name) -> System.out.println(name);
        consumer.accept("subramanian");
        consumer = System.out::println;
        consumer.accept("subramanian");
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list.forEach(System.out::println);
        ///
        Supplier<String> supplier = () -> "Hello";
        System.out.println(supplier.get());

        Predicate<Integer> isEven = number -> number % 2 == 0;
        System.out.println(isEven.test(100) ? "Even" : "Odd");
        System.out.println(isEven.test(3) ? "Even" : "Odd");

        Function<Integer, Integer> function = number -> number * 2;
        System.out.println(function.apply(10));


    }
}
