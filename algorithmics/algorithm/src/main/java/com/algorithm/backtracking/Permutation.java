package com.algorithm.backtracking;

public class Permutation {

    //permutari de 4
    public static void main(String[] args) {

        permut(6);

    }

    private static void permut(int i) {
        int sol[] = new int[i];
        for (int j = 0; j < sol.length; j++) sol[j] = 0;
        permutation(0, 0, sol);


    }

    private static void permutation(int depth, int no, int[] sol) {
        if (depth==sol.length-1 &&  checkSol(sol, depth)) printSol(sol);
        if (depth < sol.length) {
            sol[depth] = no;
            if (verifySoFar(sol, depth))
                permutation(depth + 1, 0, sol);
            if(no+1<sol.length)
                permutation(depth, no + 1, sol);


        }



    }

    private static boolean verifySoFar(int[] sol, int depth) {
        for (int i = 0; i <= depth; i++)
            for (int j = i + 1; j <= depth; j++) {
                if (sol[i] == sol[j]) return false;
            }
        return true;
    }


    private static void printSol(int[] sol) {
        for (int i = 0; i < sol.length; i++) {
            System.out.print(sol[i] + ",");
        }
        System.out.println("\n----------------------");
    }

    private static boolean checkSol(int[] sol, int level) {
        for (int i = 0; i <= level; i++) {
            for (int j = i + 1; j <= level; j++)
                if (sol[i] == sol[j]) return false;
        }
        return true;
    }


}
