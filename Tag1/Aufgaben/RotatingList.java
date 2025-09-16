package org.example.nkt.Tag1.Aufgaben;

import java.util.ArrayList;
import java.util.List;

public class RotatingList<T> extends ArrayList<T> {
    @Override
    public T get(int index) {

        if (index > this.size()) {
            index = index % this.size();
        }
        return super.get(index);
    }

    public void rotate(int x) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            list.add(get(i));
        }
        this.addAll(list);
    }
}
