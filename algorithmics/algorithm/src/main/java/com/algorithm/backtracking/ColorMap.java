package com.algorithm.backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ColorMap {

    private static int SIZE = 10;

    private static int map[][] =
            {
                    {0, 1, 0, 0, 1, 0, 1, 0, 0, 0},
                    {1, 0, 1, 0, 0, 0, 0, 1, 0, 0},
                    {0, 1, 0, 1, 0, 0, 0, 0, 1, 0},
                    {0, 0, 1, 0, 1, 0, 0, 0, 0, 1},
                    {1, 0, 0, 1, 0, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 0, 1, 1, 0},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                    {0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
                    {0, 0, 1, 0, 0, 1, 1, 0, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0, 1, 0, 0}
            };

    enum Color {
        NONE(0),
        RED(1),
        GREEN(2),
        BLUE(3),
        YELLOW(4),
        ORANGE(5);

        int order;

        Color(int order) {
            this.order = order;
        }

        int getOrder() {
            return order;
        }

    }

    ;

    private static int MAX_COLOR_USED = 4;


    public static void main(String[] args) {

        List<Color> sol = new ArrayList<>(Collections.nCopies(SIZE, Color.NONE));
        colorize(sol, 0);


    }

    private static void colorize(List<Color> sol, int depth) {
        if (depth == sol.size() && sol.get(sol.size() - 1) != Color.NONE) printSol(sol);
        else {
            Color cl = getColorFromDepth(sol, depth);
            if (cl.getOrder() < MAX_COLOR_USED) {
                if (checkIfCurrentColorIsOk(sol, cl, depth)) {
                    sol.set(depth, cl);
                    colorize(sol, depth + 1);
                } else {
                    sol.set(depth, Color.values()[cl.order + 1]);
                    colorize(sol, depth);
                }
            }
        }
    }

    private static Color getColorFromDepth(List<Color> sol, int depth) {
        return sol.get(depth) == Color.NONE ? Color.RED : sol.get(depth);
    }

    private static boolean checkIfCurrentColorIsOk(List<Color> sol, Color cl, int depth) {
        for (int i = 0; i < SIZE; i++) {
            int neighbourd = map[depth][i];
            if (neighbourd > 0 && sol.get(i) == cl) return false;
        }
        return true;
    }

    private static void printSol(List<Color> sol) {
        IntStream.range(0, SIZE).forEach(i -> System.out.print(String.format("%d-%s  ", i, sol.get(i))));
    }
}