package com.algorithm.backtracking;


import java.util.ArrayList;
import java.util.List;

public class MagnetPuzzle {

    static class Point {
        int i;
        int j;
        int val;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
        }


    }

    static class Magnet {
        Point first;
        Point second;
        boolean isEmpty;

        public Magnet(Point first, Point second, boolean isEmpty) {
            this.first = first;
            this.second = second;
            this.isEmpty = isEmpty;
        }

        public Magnet(Point first, Point second) {
            this.first = first;
            this.second = second;

        }

        public boolean isEmpty() {
            return isEmpty;
        }


        public void setEmpty(boolean empty) {
            isEmpty = empty;
        }

        public int getVal(int i, int j) {
            if (isEmpty) return 0;
            if (containsPoint(i, j)) {
                if ((first.i == i) && (first.j == j))
                    return first.val;
                if ((second.i == i) && (second.j == j))
                    return second.val;
            }
            throw new RuntimeException("coordinate outside !");
        }


        /**
         * @return true if the point is inside this magnet
         */
        boolean containsPoint(int i, int j) {
            return
                    ((first.i == i) && (first.j == j)) ||
                            ((second.i == i) && (second.j == j));
        }


    }

    static class HMagnet extends Magnet {

        public HMagnet(Point first) {
            super(first, new Point(first.i, first.j + 1));
            first.val = +1;
            second.val = -1;
        }


    }

    static class VMagnet extends Magnet {
        public VMagnet(Point first) {
            super(first, new Point(first.i + 1, first.j));
            first.val = +1;
            second.val = -1;
        }
    }


    private static List<Magnet> magnets = new ArrayList<>();


    static int M = 5, N = 6;
    static int top[] = {1, -1, -1, 2, 1, -1};
    static int bottom[] = {2, -1, -1, 2, -1, 3};
    static int left[] = {2, 3, -1, -1, -1};
    static int right[] = {-1, -1, -1, 1, -1};
    static char rules[][] = {{'L', 'R', 'L', 'R', 'T', 'T'},
            {'L', 'R', 'L', 'R', 'B', 'B'},
            {'T', 'T', 'T', 'T', 'L', 'R'},
            {'B', 'B', 'B', 'B', 'T', 'T'},
            {'L', 'R', 'L', 'R', 'B', 'B'}};

    public static void main(String[] args) {

        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++) {
                switch (rules[i][j]) {
                    case 'L':
                        magnets.add(new HMagnet(new Point(i, j)));
                        break;
                    case 'T':
                        magnets.add(new VMagnet(new Point(i, j)));
                    default:
                        //do nothing
                }
            }
    // encode the following operations: 0 do nothing, 1 mirror, 2 empty
        int sol[] = new int[magnets.size()];

        doCompute(sol,0,0);
    }


    private static void doCompute(int[] sol, int pos, int depth) {
    }
}



