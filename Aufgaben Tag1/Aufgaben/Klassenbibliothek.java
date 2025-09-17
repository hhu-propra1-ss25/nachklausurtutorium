package org.example.nkt.Tag1.Aufgaben;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

public class Klassenbibliothek {
    static List<String> liste = new ArrayList<>(List.of("161", "Alerta"));

    public static void test() {
        Set<String> set = new HashSet<>(liste);
        set.addAll(liste);

        Optional<String> optional1 = Optional.empty();
        Optional<String> optional2 = Optional.of("value");
        Map<Boolean, String> map = new HashMap<>();
        map.put(optional1.isPresent(), optional1.orElseGet(() -> "namenslos"));
        map.put(optional2.isPresent(), optional2.get());
        map.entrySet().stream().forEach(System.out::println);
        map.entrySet().stream().forEach(entry -> System.out.println(entry));
    }

    public static <T, U, R> List<R> map2(BiFunction<T, U, R> biFunction, List<T> list1, List<U> list2) {

        List<R> resultList = new ArrayList<>();

        for (int i = 0; i < list1.size(); i++) {
            R result = biFunction.apply(list1.get(i), list2.get(i));
            resultList.add(result);
        }
        return resultList;
    }
    public static void main(String[] args) {
        List<String> c1 = List.of("a", "b", "c");
        List<Integer> c2 = List.of(1, 2, 3);
        List<Double> c3 = List.of(2.5, 4.0, 9.0);

        BiFunction<String, Integer, String> concat = (a, b) -> a + b;
        List<String> r1 = map2(concat, c1, c2);
        System.out.println(r1);

        BiFunction<Integer, Double, Double> multiply = (a, b) -> a * b;
        List<Double> r2 = map2(multiply, c2, c3);
        System.out.println(r2);

        Function<String, Integer> function = string -> string.length();
        Predicate<String> predicate = string -> string.startsWith("a");
    }
}

