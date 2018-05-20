package com.algorithm.backtracking;

import java.util.Stack;

/*
We have discussed Backtracking and Knight’s tour problem in Set 1. Let us discuss Rat in a Maze as another example problem
that can be solved using Backtracking.

A Maze is given as N*N binary matrix of blocks where source block is the upper left most block i.e.,
maze[0][0] and destination block is lower rightmost block i.e., maze[N-1][N-1]. A rat starts from source and has to reach
destination. The rat can move only in two directions: forward and down.
In the maze matrix, 0 means the block is dead end and 1 means the block can be used in the path from source to destination.
Note that this is a simple version of
the typical Maze problem. For example, a more complex version can be that the rat can move in 4 directions and a more
complex version can be with limited number of moves.
 */
public class RatInAMaze {

    private enum MOVE {
        RIGHT,
        DOWN,
        STOP;

        private static MOVE next;

        static {
            RIGHT.next = DOWN;
            DOWN.next = STOP;
            STOP.next = STOP;
        }

        MOVE next() {
            return next;
        }
    }

    private static class Pair {
        public Integer x;
        public Integer y;

        public Pair(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        public Pair move(MOVE move) {
            switch (move) {
                case RIGHT:
                    return new Pair(this.x + 1, this.y);
                case DOWN:
                    return new Pair(this.x, this.y + 1);
                default:
                    throw new IllegalArgumentException();
            }

        }

        @Override
        public String toString() {
            return String.format("(%d,%d)", x, y);
        }
    }

    static int WIDTH = 4;
    static int HEIGHT = 4;

    static int maze[][] = new int[][]{
            {1, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 1, 1, 0},
            {1, 1, 1, 1}
    };

    public static void main(String[] args) {


        Stack<Pair> sol = new Stack<>();
        sol.push(new Pair(0, 0));
        findExitInMaze(sol);

    }

    private static void findExitInMaze(Stack<Pair> sol) {
        Pair lastPos = sol.peek();
        if (lastPos.x == WIDTH - 1 && lastPos.y == HEIGHT - 1) printSol(sol);
        else {
            if (canGo(lastPos, MOVE.RIGHT)) {
                sol.push(lastPos.move(MOVE.RIGHT));
                findExitInMaze(sol);
            }
            if (canGo(lastPos, MOVE.DOWN)) {
                sol.push(lastPos.move(MOVE.DOWN));
                findExitInMaze(sol);
            }
        }
        //dead end...should be remove
        sol.pop();
    }

    private static boolean canGo(Pair lastPos, MOVE move) {
        switch (move) {
            case RIGHT:
                return (lastPos.x + 1 < WIDTH && maze[lastPos.x + 1][lastPos.y] == 1);
            case DOWN:
                return (lastPos.y + 1 < HEIGHT && maze[lastPos.x][lastPos.y + 1] == 1);
            default:
                return false;
        }
    }

    private static void printSol(Stack<Pair> sol) {
        sol.stream().forEach(System.out::print);
        System.out.println("");
    }


}
