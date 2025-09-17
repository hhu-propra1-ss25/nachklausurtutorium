package org.example.nkt.Tag1.Aufgaben;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class FunktionaleProgrammierung {

    public static <T, U, R> List<R> map2(BiFunction<T, U, R> biFunction, List<T> list1, List<U> list2) {
        List<R> result = new ArrayList<>();
        for(int i = 0; i < list1.size(); i++) {
            result.add(biFunction.apply(list1.get(i), list2.get(i)));
        }

        return result;
    }
}
