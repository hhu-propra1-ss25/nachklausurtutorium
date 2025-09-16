package org.example.nkt.Tag1.Lösungen;

import java.util.List;

class IdiomatischeLösungen {
    public static void main(String args[]) {
        // Main-Methode zum manuellen testen
    }

    // Aufgabe:
    // Folgende Methoden sollen zu einer idiomatischen Lösung umgeschrieben werden

    public static void printList(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }

    public static int sumArray(List<Integer> list) {
        /*
        int count = 0;
        int summe = 0;
        while (count < list.size()) {
            summe += list.get(count);
            count++;
        }*/
        return list.stream().mapToInt(i -> i).sum();
    }

    public static boolean contains(List<String> list, String toFind) {
//        boolean gefunden = false;
//        for (String element : list) {
//            if (element.equals(toFind)) {
//                gefunden = true;
//                break;
//            }
//        }
        return list.stream().filter(toFind::equals).findFirst().isPresent();

    }

    public static int count(List<Integer> list, int value) {
//        int count = 0;
//        for (Integer integer : list) {
//            if (integer > value) {
//                count++;
//            }
//        }
        return (int) list.stream().filter(i -> i > value).count();
    }

    // WICHTIG!! Für die Folgenden Aufgaben soll jeweils ein Lambda Ausdruck benutzt
    // werden!!

    public static void printListLambda(List<Integer> list) {
//        for (Integer integer : list) {
//            System.out.println(integer);
//        }
        list.forEach(System.out::println);
    }

    // diese Methode soll jeden Eintrag der Liste inkrementieren
    public static void incrementList(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i) + 1);
        }

        list.replaceAll(e -> e + 1);

        // Das hier eher nicht
        list.forEach(e -> {
            int i = list.indexOf(e);
            list.set(i, e + 1);
        });
    }
}