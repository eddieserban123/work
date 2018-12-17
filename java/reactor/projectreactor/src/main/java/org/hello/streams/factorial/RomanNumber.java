package org.hello.streams.factorial;

import java.util.TreeMap;

class RomanNumber {
    TreeMap<Integer, String> romanMap = new TreeMap<>();

    RomanNumber() {
        romanMap.put(1000, "M");
        romanMap.put(900, "CM");
        romanMap.put(500, "D");
        romanMap.put(400, "CD");
        romanMap.put(100, "C");
        romanMap.put(90, "XC");
        romanMap.put(50, "L");
        romanMap.put(40, "XL");
        romanMap.put(10, "X");
        romanMap.put(9, "IX");
        romanMap.put(5, "V");
        romanMap.put(4, "IV");
        romanMap.put(1, "I");
    }

    String toRomanNumeral(int number) {
        int l = romanMap.floorKey(number);
        if (number == l) {
            return romanMap.get(number);
        }
        return romanMap.get(l) + toRomanNumeral(number - l);
    }

    public static void main(String[] args) {
        RomanNumber rm = new RomanNumber();
        String s = rm.toRomanNumeral(6);
        System.out.println(s);
    }
}