package org.example.nkt.Tag1.Lösungen;

import java.util.ArrayList;
import java.util.List;

public class RotatingListLösung {


    public class RotatingList<T> extends ArrayList<T> {
        @Override
        public T get(int index) {
            if (index > super.size()) {
                index = index % super.size();
            }
            return super.get(index);
        }

        public void rotate(int x) {
            List<T> listToAdd = new ArrayList<>();
            for (int i = 0; i < x; i++) {
                listToAdd.add(this.remove(i));
            }
            this.addAll(listToAdd);
        }
    }
}
