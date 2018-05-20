package com.algorithm.backtracking;

//Subset sum problem is to find subset of elements that are selected from a given set whose sum adds up to a given number K.
// We are considering the set contains non-negative values. It is assumed that the input set is unique (no duplicates are presented).

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class SubsetSum {

    private static List<Integer> ar = Arrays.asList(15, 22, 14, 26, 32, 9, 16, 8);
    private static int SUM = 53;

    public static void main(String[] args) {

        subSet(new Stack<>(), 0, 0);
    }

    private static void subSet(Stack<Integer> sol, int rootLevel, int child) {
        if (isSol(sol)) printSol(sol);
        else {
            if (rootLevel + child < ar.size()) {
                if (verifySofAr(sol, rootLevel + child)) {
                    sol.add(ar.get(rootLevel + child));
                    subSet(sol, rootLevel + 1, child);
                    sol.pop();
                } //else
                subSet(sol, rootLevel, child + 1);
            }
        }
    }

    private static boolean verifySofAr(List<Integer> sol, int root) {
        return ((sol.stream().reduce(0, (x, y) -> x + y)) + ar.get(root)) <= SUM;
    }

    private static void printSol(List<Integer> sol) {
        sol.stream().forEach(System.out::print);
        System.out.println("");
    }

    private static boolean isSol(List<Integer> sol) {
        return (sol.stream().reduce(0, (x, y) -> x + y) == SUM);
    }

}
