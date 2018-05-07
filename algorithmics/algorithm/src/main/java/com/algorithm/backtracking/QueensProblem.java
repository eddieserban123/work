package com.algorithm.backtracking;

public class QueensProblem {

    public static void main(String[] args) {

        int table[] = new int[8];
        for (int j = 0; j < table.length; j++) table[j] = 0;
        queens(0, 0, table);

    }


    private static void queens(int depth, int pos, int sol[]) {
        if (depth == sol.length && checkSol(sol)) printSol(sol);
        if (depth < sol.length) {
            sol[depth] = pos;
            if (verifySoFar(depth, sol))
                queens(depth + 1, 0, sol);
            if (pos + 1 < sol.length)
                queens(depth, pos + 1, sol);
        }
    }

    private static boolean verifySoFar(int depth, int[] sol) {
        int j;
        for (int i = 0; i <= depth; i++) {
            for (j = 0; j < i; j++) {
                if (sol[i] == sol[j]) return false;
                if (Math.abs(i - j) == Math.abs(sol[i] - sol[j])) return false;
            }
        }

        return true;
    }


    private static void printSol(int[] sol) {
        int j;
        System.out.println(" ");
        for (int i = 0; i < sol.length; i++) {
            for (j = 0; j < sol[i]; j++)
                System.out.print("X");
            System.out.print("O");
            for (int k = j + 1; k < sol.length; k++)
                System.out.print("X");
            System.out.println(" ");

        }

    }

    private static boolean checkSol(int[] sol) {
        for (int i = 0; i < sol.length; i++) {
            for (int j = 0; j < i; j++) {
                if (sol[i] == sol[j]) return false;
                if (Math.abs(i - j) == Math.abs(sol[i] - sol[j])) return false;
            }
        }
        return true;
    }
}
