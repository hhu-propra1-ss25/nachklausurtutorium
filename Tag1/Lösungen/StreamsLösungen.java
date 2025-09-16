package org.example.nkt.Tag1.Lösungen;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsLösungen {

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
            // return numbers.stream().filter(i -> isFizzBuzzableNumber(i)).toList();
            return numbers.stream().filter(FizzBuzzableNumbers::isFizzBuzzableNumber).toList();
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
            return results.stream().distinct().sorted().map(String::valueOf).toList();
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

        // Aufgabe:
        // Gegeben sei eine Liste von Integern.
        // Wir wollen eine Methode implementieren, welche uns die Häufigkeit der Zeichen
        // in den einzigartigen Integern zurückgibt.
        //
        // Bsp: Seien die Integer {1 10 -20} die Map ist: {'0': 2, '1': 2, '2': 1, '-': 1}
        // Implementiere die Methode mit genau einem Stream
        //
        // Bonuspunkte gibt es, wenn die Methode ohne Collector implementiert wird.
        public static Map<Character, Long> countDigits(List<Integer> results) {
            return results.stream()
                    .map(String::valueOf)
                    .flatMapToInt(s -> s.chars())
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        }

    }

    static class Lottos {
        record Wurf(int augenzahl, int wert) {
        };

        // Aufgabe:
        // Schreibe eine Methode Max, welche ein Array von Würfen nimmt,
        // und den größten Wert aller Würfe zurückgibt.
        public static int max(Wurf[] wuerfe) {
            return Arrays.stream(wuerfe).mapToInt(Wurf::wert).max().orElse(0);
        }

        public static int min(Wurf[] wuerfe) {
            return Arrays.stream(wuerfe).mapToInt(Wurf::wert).min().orElse(0);
        }

        // Aufgabe:
        // Schreibe eine Methode relativeMax, welche ein Array von Würfen nimmt,
        // und den Wurf mit dem relativ größten Wert, gemessen an der Augenzahl,
        // zurückgibt.
        //
        // Den relativen Wert erhalten wir durch die Formel
        // Wurf.wert / Wurf.augenzahl = relativer Wert
        //
        // Da wir nicht wissen, ob es einen Wurf gibt, müssen wir hier ein Optional
        // zurückgeben.
        public static Optional<Wurf> relativeMax(Wurf[] wuerfe) {
            return Arrays.stream(wuerfe)
                    .max(Comparator.comparingDouble(w -> w.wert() / (double) w.augenzahl()));
        }

        // Aufgabe:
        // Schreibe eine Methode, welche einen Endlosen Stream von Würfen nimmt, und
        // 'number' Würfe mit der Augenzahl 'augenzahl' zurückgibt.
        //
        // Verwende genau einen Stream
        public static Wurf[] getWuerfe(Stream<Wurf> wuerfe, int augenzahl, int number) {
            return wuerfe.filter(w -> w.augenzahl() == augenzahl).limit(number).toArray(Wurf[]::new);
        }

        // Aufgabe:
        // Wir wollen nun die Würfe nach Augenzahlen sortieren.
        // Dazu können wir eine Map verwenden, welche jeder Augenzahl die Menge an
        // Würfen mit jener Augenzahl zuweist.
        public static Map<Integer, List<Wurf>> wuerfeNachAugenzahl(List<Wurf> wuerfe) {
            return wuerfe.stream().collect(Collectors.groupingBy(Wurf::augenzahl));
        }

    }
}
