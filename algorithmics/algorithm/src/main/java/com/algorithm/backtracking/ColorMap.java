package com.algorithm.backtracking;

public class ColorMap {

    private static int SIZE=10

    private static int map[][] =
    {
            {0,1,0,0,1,0,1,0,0,0},
            {1,0,1,0,0,0,0,1,0,0},
            {0,1,0,1,0,0,0,0,1,0},
            {0,0,1,0,1,0,0,0,0,1},
            {0,0,0,1,0,1,0,0,0,0},
            {0,0,0,0,1,0,0,1,1,0},
            {1,0,0,0,0,0,0,0,1,0},
            {0,1,0,0,0,1,0,0,0,0},
            {0,0,1,0,0,1,1,0,0,0},
            {0,0,0,0,0,0,1,1,0,0}
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

        int getOrder(){
            return order;
        }

    };

    public static void main(String[] args) {




    }
}