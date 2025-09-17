package org.example.nkt.Tag1.Aufgaben;

public interface FuncInterface {
}

@FunctionalInterface
interface Comparator<T> {
    int compare(T first, T second);
    boolean equals(Object obj); // das hier ist ok, ist ja von Object

    static boolean test() {
        return false;
    }
}


