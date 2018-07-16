package com.algorithm.backtracking;

import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/*
Newspapers and magazines often have crypt-arithmetic puzzles of the form:

        SEND
      + MORE
        --------
        MONEY
        --------

        The goal here is to assign each letter a digit from 0 to 9 so that the arithmetic works out correctly.
        The rules are that all occurrences of a letter must be assigned the same digit, and no digit can be assigned to more than one letter.

        First, create a list of all the characters that need assigning to pass to Solve
        If all characters are assigned, return true if puzzle is solved, false otherwise
        Otherwise, consider the first unassigned character
        for (every possible choice among the digits not in use)
        make that choice and then recursively try to assign the rest of the characters
        if recursion sucessful, return true
        if !successful, unmake assignment and try another digit

        If all digits have been tried and nothing worked, return false to trigger backtracking

*/
public class CryptarithmeticPuzzle {

    private static Map<Character, Integer> letterAssigned = new HashMap();

    private static String str1 = "send";
    private static String str2 = "more";

    private static String result = "money";


    private static List<Character> solution;

    public static void main(String[] args) {

        solution = countLetters(str1, str2, result);
        if (solution.size() > 10) {
            System.out.println("too many letters ! exit");
            System.exit(1);
        }

        List<Integer> solNumbers = new ArrayList<>(10);
        for (int i = 0; i < solution.size(); i++) {
            solNumbers.add(-1);
        }


        decryptPuzzle(solNumbers, 0, 0);
    }

    private static void decryptPuzzle(List<Integer> solNumbers, int pos, int depth) {
        if (pos == solution.size() && checkPuzzle(solNumbers)) {
            printSol(solNumbers);
        }
        if (pos < solNumbers.size()) {
            solNumbers.set(pos, depth);
            if (checkSolSoFar(solNumbers, pos, depth))
                decryptPuzzle(solNumbers, pos + 1, 0);
            if (depth < solNumbers.size())
                decryptPuzzle(solNumbers, pos, depth + 1);

        }
    }


    private static boolean checkSolSoFar(List<Integer> solNumbers, int pos, int depth) {
        for (int i = 0; i < pos; i++) {
            if (solNumbers.get(i) == depth) return false;
        }
        return true;
    }

    private static boolean checkPuzzle(List<Integer> list) {

        int val1 = Integer.valueOf(print(str1, list));
        int val2 = Integer.valueOf(print(str2, list));
        int res = Integer.valueOf(print(result, list));
        return res == val1 + val2;

    }

    private static void printSol(List<Integer> sol) {

        print(str1, sol);
        print(str2, sol);
        print(result, sol);


    }

    private static String print(String str, List<Integer> sol) {

        String aux = str;
        for (int i = 0; i < sol.size(); i++) {
            aux = aux.replace(solution.get(i), Character.forDigit(sol.get(i), 10));
        }
        return aux;
    }


    private static List<Character> countLetters(String str1, String str2, String str3) {
        List<Character> list = new ArrayList<>();
        String res = str1.concat(str2).concat(str3);
        for (char ch : res.toCharArray()) {
            if (!check(ch, list))
                list.add(ch);
        }
        return list;
    }

    private static boolean check(Character ch, List<Character> list) {
        return list.stream().filter(c -> c.compareTo(ch) == 0).findAny().isPresent();
    }


}
