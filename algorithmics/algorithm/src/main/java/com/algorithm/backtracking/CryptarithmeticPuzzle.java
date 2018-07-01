package com.algorithm.backtracking;

import java.util.HashMap;
import java.util.Map;
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

    private static Map<Character, Integer> resolvedChars = new HashMap<>();
    private static Map<Integer, Character> resolvedNumbers = new HashMap<>();

    public static void main(String[] args) {

        int noLetters;
        str1 = new StringBuilder(str1).reverse().toString().toLowerCase();
        str2 = new StringBuilder(str2).reverse().toString().toLowerCase();
        result = new StringBuilder(result).reverse().toString().toLowerCase();
        noLetters = countLetters(str1, str2, result);
        System.out.println("numbers of letters are " + noLetters);
        decryptPuzzle(0, 0, 0);
    }

    private static void decryptPuzzle(int pos, int depth, int carry) {
        if (pos == result.length()) printSol();
        else {
            Character ch1 = pos >= str1.length() ? null : str1.charAt(pos);
            //check if cha1 is already solved
            analyzeChar(pos, depth, ch1, carry);
            Character ch2 = pos >= str2.length() ? null : str2.charAt(pos);
            analyzeChar(pos, depth, ch2, carry);
            //check the chars in result
            int sum = resolvedChars.get(ch1) + resolvedChars.get(ch2) + carry;
            Character chRes = resolvedNumbers.get(sum % 10);
            if (chRes != null && chRes.compareTo(result.charAt(pos)) == 0)
                decryptPuzzle(pos + 1, 0, sum / 10);  //coul be optimized ? depth = depth ?
            decryptPuzzle(pos, depth + 1, carry);
        }
    }

    private static void analyzeChar(int pos, int depth, Character ch1, int carry) {
        if (resolvedChars.get(ch1) == null) {
            resolvedNumbers.put(depth, ch1);
            resolvedChars.put(ch1, depth);
            decryptPuzzle(pos, depth + 1, carry);
        }
    }

    private static void printSol() {
        resolvedChars.entrySet().stream().forEach(e -> {
                    System.out.println(e.getKey() + " " + e.getValue() + " ");
                }
        );
    }

    private static int countLetters(String str1, String str2, String str3) {
        Map<Character, Integer> map = new HashMap<>();
        String res = str1.concat(str2).concat(str3);
        for (char ch : res.toCharArray()) {
            map.putIfAbsent(ch, 1);
        }
        return map.values().stream().reduce(0, (x, y) -> x + y);
    }
}
