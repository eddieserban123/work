package com.algorithm.backtracking;

import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

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


    private static Map<Character, Integer> solution;

    public static void main(String[] args) {

        solution = countLetters(str1, str2, result);
        if (solution.size() > 10) {
            System.out.println("too many letters ! exit");
            System.exit(1);
        }

        List<Integer> solNumbers = Collections.nCopies(solution.size(),0);

        decryptPuzzle(solNumbers,0, 0);
    }

    private static void decryptPuzzle(List<Integer> solNumbers, int pos, int depth) {
        if (pos == solution.size() && checkPuzzle()) {
            printSol();
        } else {
            if(depth<solution.size()) {
                solNumbers.set(pos, depth);
                decryptPuzzle(solNumbers,pos+1,0);
            }

            solution.put(pos,)
        }
    }

    private static boolean checkPuzzle() {
    }

    private static void printSol() {
        print(str1);
        print(str2);
        print(result);


    }

    private static void print(String str) {
        String res = solution.entrySet().stream().reduce(str, (accStr, es) ->
                        accStr.replace(es.getKey(), Character.forDigit(es.getValue(), 10)),
                (il1, il2) -> il1);
        System.out.println(str);
        System.out.println(res);
    }


    private static Map<Character, Integer> countLetters(String str1, String str2, String str3) {
        Map<Character, Integer> map = new HashMap<>();
        String res = str1.concat(str2).concat(str3);
        for (char ch : res.toCharArray()) {
            map.putIfAbsent(ch, 0);
        }
        return map;
    }


}
