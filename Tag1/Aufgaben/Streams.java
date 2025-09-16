package org.example.nkt.Tag1.Aufgaben;

import org.example.nkt.Tag1.Lösungen.StreamsLösungen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Streams {

    public static List<String> map(List<Integer> ints) {
        // Aufgabe:
        // Schreibe den Code so um, dass genau ein Stream und kein imperativer Code
        // verwendet wird.
        List<String> strs = new ArrayList<>(ints.size());
        for (Integer integer : ints) {
            strs.add(String.valueOf(integer));
        }
        ints.stream().map(zahl -> String.valueOf(zahl)).toList();
        return strs;
    }

    public static List<Integer> filter(List<Integer> ints) {
        // Aufgabe:
        // Schreibe folgenden Code in einen Stream um.
        List<Integer> list = new ArrayList<>();
        for (Integer integer : ints) {
            if (integer > 10) {
                list.add(integer);
            }
        }
        ints.stream().filter(zahl -> zahl > 10).toList();
        return list;
    }

    public static List<String> filterMap(List<Integer> ints) {
        // Aufgabe:
        // Schreibe folgenden Code in einen Stream um.
        List<String> strs = new ArrayList<>(ints.size());
        for (Integer integer : ints) {
            if (integer > 10) {
                strs.add(String.valueOf(integer));
            }
        }
        ints.stream().filter(integer -> integer > 10).map(String::valueOf).toList();
        return strs;
    }

    public static List<String> mapFilterMap(List<Integer> ints) {
        // Aufgabe:
        // Schreibe folgenden Code in einen Stream um.
        List<String> strs = new ArrayList<>();
        for (Integer integer : ints) {
            double div = integer / 10D;
            if (div > 10) {
                strs.add(String.valueOf(integer));
            }
        }
        ints.stream()
                .map(integer -> integer / 10D)
                .filter(integer -> integer > 10)
                .map(String::valueOf)
                .toList();

        return strs;
    }

    public static List<String> lastDigitsAreHard(List<Integer> ints) {
        // Aufgabe:
        // Schreibe folgenden Code in einen Stream um.
        List<String> lastDigits = new ArrayList<>();
        for (Integer integer : ints) {
            int lastDigit = integer % 10;
            String charRepr = String.valueOf(lastDigit);
            lastDigits.add(charRepr);
        }
        ints.stream()
                .map(integer -> integer % 10)
                .map(String::valueOf)
                .toList();
        return lastDigits;
    }


    public static void main(String[] args) {

    }
    static class FizzBuzzableNumbers {
        // Seien die FizzBuzz-baren Zahlen die ganzen Zahlen, welche sowohl durch 3, als
        // auch durch 5
        // teilbar sind.
        // Gegeben seien folgende 2 Methoden:
        public static boolean isFizzBuzzableNumber(int i) {
            // Hier nichts ändern
            return (i % 3) + (i % 5) == 0;
        }

        public static List<Integer> obtainFizzBuzzableNumbers(List<Integer> numbers) {
            // Schreibe einen Stream, welcher die FizzBuzz-baren Zahlen bestimmt und als
            // Liste zurückgibt.
            return numbers.stream()
                    .filter(FizzBuzzableNumbers::isFizzBuzzableNumber)
                    .toList();

        }
    }

    static class BulkDataTransforms {

        // Aufgabe:
        // Gegeben sei eine Liste von Integern. Wir wollen diese Integer sortiert als
        // String ausgeben, wobei Duplikate entfernt werden sollen.
        // Implementiere dazu folgende Methode, wobei genau ein Stream verwendet werden
        // soll.
        // Verwende Methodenreferenzen wo möglich.
        public static List<String> distinctResultsOrdered(List<Integer> results) {

            return results.stream()
                    .sorted()
                    .distinct()
                    .map(String::valueOf)
                    .toList();
        }

        // Aufgabe:
        // Gegeben sei folgende Methode.
        // Schaue dir diese Methode an und beschreibe was diese Methode macht.
        private static <K, V, M extends Map<K, V>> BiConsumer<M, M> mapMerger(BinaryOperator<V> merger) {
            return (m1, m2) -> {
                for (Map.Entry<K, V> e : m2.entrySet())
                    m1.merge(e.getKey(), e.getValue(), merger);
            };
        }

        // Aufgabe (Schwer):
        // Gegeben sei eine Liste von Integern.
        // Wir wollen eine Methode implementieren, welche uns die Häufigkeit der Zeichen
        // in den einzigartigen Integern zurückgibt.
        //
        // Bsp: Seien die Integer {1 10 -20} die Map ist: {'0': 2, '1': 2, '2': 1, '-':1}
        // Implementiere die Methode mit genau einem Stream
        //
        // Bonuspunkte gibt es, wenn die Methode ohne Collector implementiert wird.
        public static Map<Character, Long> countDigits(List<Integer> results) {
            return results.stream()
                    .map(String::valueOf)
                    .flatMapToInt(string -> string.chars())
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.groupingBy(c -> c,Collectors.counting()));
        }

    }
}
