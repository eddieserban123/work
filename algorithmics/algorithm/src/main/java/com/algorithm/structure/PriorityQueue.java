package com.algorithm.structure;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<T extends Comparable> {

    private List<T> data;

    public PriorityQueue() {
        data = new ArrayList<>(16);
    }

    public void push(T elem) {
        int pos = data.size();
        data.add(elem);
        if (pos == 0) {
            return;
        }
        int parent_pos = (pos - 1) / 2;
        while (parent_pos >= 0) {
            //check pos with parent_pos
            if (data.get(parent_pos).compareTo(data.get(pos)) >=0)
                return;
            swap(parent_pos, pos);
            pos = parent_pos;
            parent_pos = (pos - 1) / 2;
            //else swap
        }
    }


    private void swap(int i1, int i2) {
        T aux = data.get(i1);
        data.set(i1, data.get(i2));
        data.set(i2, aux);

    }

    public List<T> getData() {
        return data;
    }

}
