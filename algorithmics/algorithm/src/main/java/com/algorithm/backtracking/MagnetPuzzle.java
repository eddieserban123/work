package com.algorithm.backtracking;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MagnetPuzzle {

    static class Point {
        int i;
        int j;
        int val;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public Point(int i, int j, int val) {
            this.i = i;
            this.j = j;
            this.val = val;
        }


    }

    static class Magnet {
        Point first;
        Point second;

        public Magnet(Point first, Point second) {
            this.first = first;
            this.second = second;
        }


        public int getVal(int i, int j) {
            if (containsPoint(i, j)) {
                if ((first.i == i) && (first.j == j))
                    return first.val;
                if ((second.i == i) && (second.j == j))
                    return second.val;
            }
            throw new RuntimeException("coordinate outside !");
        }

        public Magnet mirror() {
            return new Magnet(new Point(first.i, first.j, -first.val),
                    new Point(second.i, second.j, -second.val));
        }

        public Magnet empty() {
            return new Magnet(new Point(first.i, first.j, 0),
                    new Point(second.i, second.j, 0));
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

        doCompute(sol, 0, 0);
    }


    private static void doCompute(int[] sol, int pos, int depth) {
        if (pos == sol.length && checkSol(sol, sol.length)) printSol(sol);
        if (pos < sol.length) {
            if (checkSol(sol, pos))
                doCompute(sol,pos+1,0);
            if(depth<3)
                doCompute(sol,pos,depth+1);
        }
    }



    private static void printSol(int[] sol) {
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++) {
                System.out.print(getMagnetContaintThisPoint(sol, sol.length, i, j).getVal(i, j));
            }
        System.out.println();
    }

    private static Magnet getMagnetContaintThisPoint(int[] sol, int n, int i, int j) {
        return IntStream.range(0, sol.length).filter(idx ->
                magnets.get(idx).containsPoint(i, j)).mapToObj(id ->
        {
            Magnet m = magnets.get(id);
            if(id>=n) return m.empty();
            switch (sol[id]) {
                case 0:
                    return m;
                case 1:
                    return m.mirror();
                default:
                    return m.empty();
            }
        }).findFirst().get();
    }

    private static boolean checkSol(int[] sol, int n) {
        int sumP, sumN = 0;
        for (int i = 0; i < M; i++) {
            sumP = sumN = 0;
            for (int j = 0; j < N; j++) {
                if (getMagnetContaintThisPoint(sol, n, i, j).getVal(i, j) < 0)
                    sumN++;
                if (getMagnetContaintThisPoint(sol, n,i, j).getVal(i, j) > 0)
                    sumP++;
            }
            if (left[i] >= 0 && left[i] != sumP)
                return false;
            if (right[i] >= 0 && right[i] != sumN)
                return false;
        }

        for (int j = 0; j < N; j++) {
            sumP = sumN = 0;
            for (int i = 0; i < M; i++) {
                if (getMagnetContaintThisPoint(sol,n, i, j).getVal(i, j) < 0)
                    sumN++;
                if (getMagnetContaintThisPoint(sol, n, i, j).getVal(i, j) > 0)
                    sumP++;
            }
            if (top[j] >= 0 && top[j] != sumP)
                return false;
            if (bottom[j] >= 0 && bottom[j] != sumN)
                return false;
        }
        return true;
    }
}



