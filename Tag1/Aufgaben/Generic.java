package org.example.nkt.Tag1.Aufgaben;

class Generic {
    // Fragen:
    // Warum wirft die main Method Compiler fehler?
    // Verändern sie folgende Methode, sodass die main Method keine Compiler fehler
    // mehr wirft
    public static void printStack(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Integer[] array = { 1, 2, 3, 8, 9, 10 };
        Double[] doubleArray = { 1.1, 2.2, 3.3, 6.6, 7.7 };
        printStack(array);
        printStack(doubleArray); // falscher Typ
        // Bonusfrage: Funktioniert folgender Aufruf? Wenn nein warum nicht?
        int[] array1 = {1, 2, 3, 8, 9, 10}; // kein Autoboxing auf Arrays
        printStack(array1);
    }
}