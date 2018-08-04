package com.algorithm.backtracking;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    static char rules[][] =
           {{'L', 'R', 'L', 'R', 'T', 'T'},
            {'L', 'R', 'L', 'R', 'B', 'B'},
            {'T', 'T', 'T', 'T', 'L', 'R'},
            {'B', 'B', 'B', 'B', 'T', 'T'},
            {'L', 'R', 'L', 'R', 'B', 'B'}};

    private static int solAux[][] = new int[M][N];

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
        long startTime = System.currentTimeMillis();
        doCompute(sol, 0, 0);
        System.out.println("duration " + (System.currentTimeMillis() - startTime));
    }

    private static void arrangeMagnets(int[] sol) {
      arrangeMagnets(sol,magnets.size()-1);

    }


    private static void arrangeMagnets(int[] sol, int n) {
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++) {
                solAux[i][j] = -2;
            }
        IntStream.rangeClosed(0, n).mapToObj(i ->
        {
            Magnet m = magnets.get(i);
            switch (sol[i]) {
                case 0:
                    return m;
                case 1:
                    return m.mirror();
                default:
                    return m.empty();
            }
        }).forEach(m -> {
            setPoint(m.first);
            setPoint(m.second);
        });

    }

    private static void setPoint(Point first) {
        solAux[first.i][first.j] = first.val;
    }


    private static void doCompute(int[] sol, int pos, int depth) {
        if (pos == sol.length && checkSol(sol, sol.length))
            printSol(sol);
        if (pos < sol.length) {
            sol[pos]=depth;
            if (checkNeighbours(sol, pos))
                doCompute(sol, pos + 1, 0);
            if (depth < 2)
                doCompute(sol, pos, depth + 1);
        }
    }


    private static void printSol(int[] sol) {
        arrangeMagnets(sol);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%2d",solAux[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }


    private static boolean checkSol(int[] sol, int n) {
        int sumP, sumN = 0;
        for (int i = 0; i < M; i++) {
            sumP = sumN = 0;
            for (int j = 0; j < N; j++) {
                if (solAux[i][j] == -1)
                    sumN++;
                if (solAux[i][j] == 1)
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
                if (solAux[i][j] == -1)
                    sumN++;
                if (solAux[i][j] == 1)
                    sumP++;
            }
            if (top[j] >= 0 && top[j] != sumP)
                return false;
            if (bottom[j] >= 0 && bottom[j] != sumN)
                return false;
        }
        return true;
    }


    private static boolean checkNeighbours(int[] sol, int n) {
        arrangeMagnets(sol,n);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                int nb = (i == M - 1) ? 0 : solAux[i + 1][j];
                int nr = (j == N - 1) ? 0 : solAux[i][j + 1];
                if ((solAux[i][j] != 0 && solAux[i][j]  !=-2) &&
                    (solAux[i][j] == nr || solAux[i][j] == nb))
                    return false;
            }
        }
        return true;
    }
}



