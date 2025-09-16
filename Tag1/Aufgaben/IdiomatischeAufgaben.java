package org.example.nkt.Tag1.Aufgaben;

import java.util.List;

class IdiomatischeAufgaben {
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
        int count = 0;
        int summe = 0;
        while (count < list.size()) {
            summe += list.get(count);
            count++;
        }
        return summe;
    }

    public static boolean contains(List<String> list, String toFind) {
        boolean gefunden = false;
        for (String element : list) {
            if (element.equals(toFind)) {
                gefunden = true;
                break;
            }
        }
        return gefunden;
    }

    public static int count(List<Integer> list, int value) {
        int count = 0;
        for (Integer integer : list) {
            if (integer > value) {
                count++;
            }
        }
        return count;
    }

    // WICHTIG!! Für die Folgenden Aufgaben soll jeweils ein Lambda Ausdruck benutzt
    // werden!!

    public static void printListLambda(List<Integer> list) {
        for (Integer integer : list) {
            System.out.println(integer);
        }

    }

    // diese Methode soll jeden Eintrag der Liste inkrementieren
    public static void incrementList(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i) + 1);
        }
    }
}
