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
            if (data.get(parent_pos).compareTo(data.get(pos)) >= 0)
                return;
            swap(parent_pos, pos);
            pos = parent_pos;
            parent_pos = (pos - 1) / 2;
            //else swap
        }
    }

    public T take() {
        T ret = data.get(0);
        if(data.size()==1) {
            data.remove(0);
            return ret;
        }
        data.set(0, data.remove(data.size() - 1));
        int pos = 0;
        T root = data.get(0);
        int size = data.size();
        while (true) {
            root = data.get(pos);
            if ((2 * pos + 2) < size) {  //there are two children
                T leftChild = data.get(2 * pos + 1);
                T rightChild = data.get(2 * pos + 2);
                if (leftChild.compareTo(rightChild) >= 0) {  //left is bigger
                    if (root.compareTo(leftChild) >= 0) break;
                    swap(pos, 2 * pos + 1);
                    pos = 2 * pos + 1;
                } else {
                    if (root.compareTo(rightChild) >= 0) break;
                    swap(pos, 2 * pos + 2);
                    pos = 2 * pos + 2;
                }


            } else if ((2 * pos + 1) < size) {  //there are 1 children (left)
                T leftChild = data.get(2 * pos + 1);
                if (root.compareTo(leftChild) >= 0) break;
                swap(pos, 2 * pos + 1);
                break;
            } else break;
        }
        return ret;

    }

    public boolean isEmpty(){
        return data.isEmpty();
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
