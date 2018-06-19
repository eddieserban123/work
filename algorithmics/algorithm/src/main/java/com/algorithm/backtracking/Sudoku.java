package com.algorithm.backtracking;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {

    private static int SIZE = 9;

    private static int mat[][] = {
            {3, 0, 6, 5, 0, 8, 4, 0, 0},
            {5, 2, 0, 0, 0, 0, 0, 0, 0},
            {0, 8, 7, 0, 0, 0, 0, 3, 1},
            {0, 0, 3, 0, 1, 0, 0, 8, 0},
            {9, 0, 0, 8, 6, 3, 0, 0, 5},
            {0, 5, 0, 0, 9, 0, 6, 0, 0},
            {1, 3, 0, 0, 0, 0, 2, 5, 0},
            {0, 0, 0, 0, 0, 0, 0, 7, 4},
            {0, 0, 5, 2, 0, 6, 3, 0, 0}
    };

    private static class Pair {
        public int i;
        public int j;

        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static void main(String[] args) {
        List<Pair> zeros = findZeros();

        solveSudoku(zeros, 0);


    }

    private static void solveSudoku(List<Pair> zeros, int depth) {
        if (isSol(zeros, depth)) printSol();
        else {
            int val = zeros.get(depth);

             if(checkCurrentValue(zeros, depth)) {

             }

        }
    }

    private static boolean checkCurrentValue(List<Pair> zeros, int depth) {
        Pair pair = zeros.get(depth);
        int val = mat[pair.i][pair.j];
        //check line
        for(int i=0;i<SIZE;i++) {
            if(i!= pair.i && mat[pair.i][i]==val) return false;
            if(i!= pair.j && mat[i][pair.j]==val) return false;
        }
        return  true;
    }

    private static void printSol() {
        System.out.println("");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private static boolean isSol(List<Pair> zeros, int depth) {
        return depth == zeros.size() + 1;
    }

    private static List<Pair> findZeros() {
        List<Pair> list = new ArrayList();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                if (mat[i][j] == 0) list.add(new Pair(i, j));
            }

        return list;
    }

}
