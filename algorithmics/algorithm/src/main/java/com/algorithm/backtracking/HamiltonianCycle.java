package com.algorithm.backtracking;

//       (0)--(1)--(2)
//        |   / \   |
//        |  /   \  |
//        | /     \ |
//       (3)-------(4)

import java.util.LinkedHashSet;
import java.util.Set;

public class HamiltonianCycle {

    private static int SIZE = 5;

    private static int[][] map = {
            {0, 1, 0, 1, 0},
            {1, 0, 1, 1, 1},
            {0, 1, 0, 0, 1},
            {1, 1, 0, 0, 1},
            {0, 1, 1, 1, 0}
    };

    public static void main(String[] args) {

        Set<Integer> markedPath = new LinkedHashSet<>();
        walk(markedPath, 0);

    }

    private static void walk(Set<Integer> markedPath, int element) {

        if (isSol(markedPath)) {
            printSol(markedPath);
        } else {
            if (!markedPath.contains(element)) {
                markedPath.add(element);
                int nth = 0;
                int nextElem;
                do {
                    nextElem = next(element, nth++);
                    if (nextElem >= 0) {
                        walk(markedPath, nextElem);
                    }
                }
                while (nextElem >= 0);
                markedPath.remove(element);
            }
        }
        //  markedPath.remove(element);
    }

    private static boolean isSol(Set<Integer> markedPath) {
        if(markedPath.size() == SIZE) {
            int lastEl = (int) markedPath.stream().toArray()[markedPath.size() - 1];
            int firstEl = (int) markedPath.stream().toArray()[0];
            return (markedPath.size() == SIZE && thereisAPathFrom(lastEl, firstEl));
        }
        return false;
    }

    private static boolean thereisAPathFrom(int lastEl, int firstEl) {
        return map[lastEl][firstEl] == 1;
    }

    private static Integer next(int elem, int nth) {
        int npos = nth + elem;
        for (int i = 0, j = elem; i < SIZE; i++) {
            if (map[elem][i] == 1 && j++ == npos) {
                return i;
            }
        }
        return -1;
    }

    private static void printSol(Set<Integer> markedPath) {
        markedPath.stream().forEach(el ->
                System.out.println(el + " "));
        System.out.println("");
    }
}
